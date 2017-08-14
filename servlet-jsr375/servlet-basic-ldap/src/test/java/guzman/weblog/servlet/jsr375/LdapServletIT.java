package guzman.weblog.servlet.jsr375;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class LdapServletIT {

  private static final String BASIC_SERVLET = "http://localhost:8080/servlet-basic-ldap/ldapServlet";
  private static URL servletUrl;
  private HttpURLConnection httpURLConnection;

  @BeforeClass
  public static void setUpClass() throws Exception {
    servletUrl = new URL(BASIC_SERVLET);
  }

  @BeforeMethod
  public void beforeMethod() throws IOException {
    httpURLConnection = (HttpURLConnection) servletUrl.openConnection();
    httpURLConnection.setRequestMethod("GET");
  }

  @AfterMethod
  public void afterMethod() {
    httpURLConnection.disconnect();
  }

  @Test
  public void testDoGetValidCredentials() throws IOException {
    System.out.println("doGetValidCredentials");
    httpURLConnection.setRequestProperty("Authorization", "Basic dGVzdFVzZXI6dGVzdFBhc3N3b3Jk");

    String result = inputStreamToString(httpURLConnection.getInputStream());
    String expected = "Caller testUser in role USER";
    assertEquals(result, expected);
  }

  @Test
  public void testDoGetInvalidPassword() throws IOException {
    System.out.println("doGetInvalidPassword");
    httpURLConnection.setRequestProperty("Authorization", "Basic dGVzdFVzZXI6aW52YWxpZFBhc3N3b3Jk");
    assertThrows(IOException.class, () -> {
      inputStreamToString(httpURLConnection.getInputStream());
    });
  }

  @Test
  public void testDoGetNoCredentials() {
    System.out.println("doGetNoCredentials");
    assertThrows(IOException.class, () -> {
      inputStreamToString(httpURLConnection.getInputStream());
    });
  }

  private String inputStreamToString(InputStream inputStream) {
    String streamAsString;
    try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
      streamAsString = scanner.useDelimiter("\\A").next();
    }
    return streamAsString;
  }
}
