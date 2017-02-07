package guzman.weblog.jaspic.basic;

import java.security.Principal;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class HeaderBasicAuthModuleTest {
  
  private final Map<String, String> options;
  private final HeaderBasicAuthModule module = new HeaderBasicAuthModule();
  private final CallbackHandler handler = mock(CallbackHandler.class);
  private final MessagePolicy mockRequestPolicy = mock(MessagePolicy.class);
  
  public HeaderBasicAuthModuleTest() {
    options = Collections.unmodifiableMap(new HashMap());
    when(mockRequestPolicy.isMandatory()).thenReturn(false);
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
    module.initialize(mockRequestPolicy, null, handler, options);
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }

  /**
   * Test of getSupportedMessageTypes method, of class HeaderBasicAuthModule.
   */
  @Test
  public void testGetSupportedMessageTypes() {
    System.out.println("getSupportedMessageTypes");
    Class<?>[] messageTypes = new Class<?>[]{HttpServletRequest.class, HttpServletResponse.class};
    assertEquals(module.getSupportedMessageTypes(), messageTypes);
  }

  @Test
  public void testValidateRequestSecure() throws AuthException {
    final MessageInfo messageInfo = mock(MessageInfo.class);
    final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
    when(messageInfo.getRequestMessage()).thenReturn(servletRequest);
    when(messageInfo.getResponseMessage()).thenReturn(servletResponse);
    final Subject client = new Subject();
    
    // Connections that are not secure must return SEND_FAILURE by default
    assertEquals(module.validateRequest(messageInfo, client, null), AuthStatus.SEND_FAILURE);
  }
  
  @Test
  public void testValidateRequestAuthHeader() throws AuthException {
    final MessageInfo messageInfo = mock(MessageInfo.class);
    final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
    when(messageInfo.getRequestMessage()).thenReturn(servletRequest);
    when(messageInfo.getResponseMessage()).thenReturn(servletResponse);
    final Subject client = new Subject();
    // Return TRUE for isSecure()
    when(servletRequest.isSecure()).thenReturn(Boolean.TRUE);
    // Token authorisation mandatory for all resources except for authentication
    when(mockRequestPolicy.isMandatory()).thenReturn(Boolean.FALSE);
    // HttpServletRequest header returns null;
    assertEquals(module.validateRequest(messageInfo, client, null), AuthStatus.FAILURE);
  }
  
  @Test
  public void testValidateRequestAuthBasic() throws AuthException {
    final MessageInfo messageInfo = mock(MessageInfo.class);
    final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    when(messageInfo.getRequestMessage()).thenReturn(servletRequest);
    final Subject client = new Subject();
    // Return TRUE for isSecure()
    when(servletRequest.isSecure()).thenReturn(Boolean.TRUE);
    // Token authorisation mandatory for all resources except for authentication
    when(mockRequestPolicy.isMandatory()).thenReturn(Boolean.FALSE);
    
    when(servletRequest.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNzd29yZA==");
    PasswordValidationCallback pwc = mock(PasswordValidationCallback.class);
    when(pwc.getResult()).thenReturn(Boolean.TRUE);
    module.setPasswordValidationCallback(pwc);
    assertEquals(module.validateRequest(messageInfo, client, null), AuthStatus.SUCCESS);
  }

  /**
   * Test of secureResponse method, of class HeaderBasicAuthModule.
   */
  @Test
  public void testSecureResponse() throws Exception {
    System.out.println("secureResponse");
    final MessageInfo messageInfo = mock(MessageInfo.class);
    final Subject subject = new Subject();
    assertEquals(module.secureResponse(messageInfo, subject), AuthStatus.SEND_SUCCESS);
  }

  /**
   * Test of cleanSubject method, of class HeaderBasicAuthModule.
   */
  @Test
  public void testCleanSubject() throws Exception {
    System.out.println("cleanSubject");
    Subject subject = new Subject();
    Principal principal = () -> "testPrincipal";
    subject.getPrincipals().add(principal);
    module.cleanSubject(null, subject);
    assertEquals(subject.getPrincipals().size(), 0);
  }
  
}
