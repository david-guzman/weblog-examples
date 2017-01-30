package guzman.weblog.jaspic.basic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class HeaderBasicAuthModule implements ServerAuthModule {
  
  private static final Class<?>[] MESSAGE_TYPES = new Class<?>[]{
    HttpServletRequest.class, HttpServletResponse.class
  };

  private MessagePolicy requestPolicy;
  private CallbackHandler handler;
  private Map options;
  // Not secure connections must be allowed explicitly
  private boolean allowNotSecure = false;
  private static final String ALLOW_NOTSECURE_KEY = "allow.notsecure";
  private static final String BASIC = "Basic";
  private static final String AUTHORISATION_HEADER = "Authorization";
  private static final String DEF_NOT_SEC = "false";
  private static final String DEF_ENC = "UTF-8";
  private PasswordValidationCallback passwordValidationCallback;

  @Override
  public void initialize(
          MessagePolicy requestPolicy, 
          MessagePolicy responsePolicy, 
          CallbackHandler handler, 
          Map options) throws AuthException {
    this.requestPolicy = requestPolicy;
    this.handler = handler;
    this.options = options;
    if (null != this.options) {
      allowNotSecure = Boolean.parseBoolean(
                (String) options.getOrDefault(ALLOW_NOTSECURE_KEY, DEF_NOT_SEC)
      );
    }
  }

  @Override
  public Class[] getSupportedMessageTypes() {
    return Arrays.copyOf(MESSAGE_TYPES, MESSAGE_TYPES.length);
  }

  @Override
  public AuthStatus validateRequest(
          MessageInfo messageInfo, 
          Subject clientSubject, 
          Subject serviceSubject
  ) throws AuthException {
    final HttpServletRequest req = (HttpServletRequest) messageInfo.getRequestMessage();
    
    // Evaluate whether not secure connections are allowed against policy
    if (!req.isSecure() && !allowNotSecure) {
      return AuthStatus.SEND_FAILURE;
    }
    
    if (!requestPolicy.isMandatory()) {

      final String userName = readAuthenticationHeader(messageInfo, clientSubject);

      if (null == userName) {
        return AuthStatus.FAILURE;
      }

      return AuthStatus.SUCCESS;
      
    } else {
      return AuthStatus.SUCCESS;
    }
  }

  @Override
  public AuthStatus secureResponse(MessageInfo mi, Subject sbjct) throws AuthException {
    return AuthStatus.SEND_SUCCESS;
  }

  @Override
  public void cleanSubject(MessageInfo mi, Subject subject) throws AuthException {
    if (null != subject) {
      subject.getPrincipals().clear();
    }
  }
  
  private String readAuthenticationHeader(MessageInfo msgInfo, Subject subj) throws AuthException {

    HttpServletRequest request = (HttpServletRequest) msgInfo.getRequestMessage();
    String header = request.getHeader(AUTHORISATION_HEADER);
    
    if (!isEmpty(header) && header.startsWith(BASIC + " ")) {

      header = header.substring(6).trim();
      
      // Decode and parse the authorization header
      byte[] headerBytes = header.getBytes(Charset.forName(DEF_ENC));
      byte[] decBytes = Base64.getDecoder().decode(headerBytes);
      String decoded = new String(decBytes, Charset.forName(DEF_ENC));

      int colon = decoded.indexOf(':');
      if (colon <= 0 || colon == decoded.length() - 1) {
        return (null);
      }

      String username = decoded.substring(0, colon);
      
      // use the callback to ask the container to
      // validate the password      
      if (null == passwordValidationCallback) {
        passwordValidationCallback = new PasswordValidationCallback(
              subj, username, decoded.substring(colon + 1).toCharArray());
      }
      try {
        handler.handle(new Callback[]{passwordValidationCallback});
        passwordValidationCallback.clearPassword();
      } catch (IOException | UnsupportedCallbackException ex) {
        AuthException ae = new AuthException();
        ae.initCause(ex);
        throw ae;
      }

      if (passwordValidationCallback.getResult()) {
        return username;
      }
    }
    return null;
  }
  
  protected void setPasswordValidationCallback(PasswordValidationCallback pwdValidationCallback) {
    this.passwordValidationCallback = pwdValidationCallback;
  }
  
  private boolean isEmpty(String text) {
    return text == null || text.isEmpty();
  }
}
