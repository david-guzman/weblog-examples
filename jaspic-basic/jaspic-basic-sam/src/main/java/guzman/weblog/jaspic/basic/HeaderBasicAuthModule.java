package guzman.weblog.jaspic.basic;

import java.util.Arrays;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
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

  @Override
  public void initialize(MessagePolicy mp, MessagePolicy mp1, CallbackHandler ch, Map map) throws AuthException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Class[] getSupportedMessageTypes() {
    return Arrays.copyOf(MESSAGE_TYPES, MESSAGE_TYPES.length);
  }

  @Override
  public AuthStatus validateRequest(MessageInfo mi, Subject sbjct, Subject sbjct1) throws AuthException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public AuthStatus secureResponse(MessageInfo mi, Subject sbjct) throws AuthException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void cleanSubject(MessageInfo mi, Subject sbjct) throws AuthException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
