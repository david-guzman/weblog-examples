package guzman.weblog.jsr375.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashSet;

import static java.util.Arrays.asList;

@ApplicationScoped
public class RestAuthenticationMechanism implements HttpAuthenticationMechanism {

  private static final String DEF_ENC = "UTF-8";

  @Override
  public AuthenticationStatus validateRequest(
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse,
    HttpMessageContext httpMessageContext
  ) throws AuthenticationException {

    String header = httpServletRequest.getHeader("Authorization");

    final String userName = readAuthenticationHeader(header);

    if (!isEmpty(userName)) {
      CredentialValidationResult valRes = new CredentialValidationResult("reza", new HashSet<>(asList("foo", "bar")));
      return httpMessageContext.notifyContainerAboutLogin(valRes);
    }
    return httpMessageContext.doNothing();
  }

  private String readAuthenticationHeader(String header) {
    if (!isEmpty(header) && header.startsWith("Basic ")) {
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
    }
    return null;
  }

  private boolean isEmpty(String text) {
    return text == null || text.isEmpty();
  }
}
