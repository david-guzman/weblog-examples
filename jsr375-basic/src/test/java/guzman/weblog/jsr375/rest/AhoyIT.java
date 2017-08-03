package guzman.weblog.jsr375.rest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AhoyIT {

  private static WebTarget target;
  private final static String TARGET_URL = "http://localhost:8081/jsr375-basic/rest";

  public AhoyIT() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
    ClientConfig config = new ClientConfig();
    final Client restClient = ClientBuilder.newClient(config);
    target = restClient.target(TARGET_URL);
  }

  @Test
  public void testDoGetValidCredentials() {
    System.out.println("doGetValidCredentials");
    String result = target.request(MediaType.TEXT_PLAIN_TYPE)
      .header("Authorization", "Basic dGVzdFVzZXI6dGVzdFBhc3N3b3Jk")
      .get(String.class);
    String expResult = "goGet: caller in role USER";
    assertEquals(result, expResult);
  }

  @Test
  public void testDoGetInvalidPassword() {
    System.out.println("doGetInvalidPassword");
    assertThrows(NotAuthorizedException.class, () -> {target.request(MediaType.TEXT_PLAIN_TYPE)
      .header("Authorization", "Basic dGVzdFVzZXI6aW52YWxpZFBhc3N3b3Jk")
      .get(String.class);});
  }

  @Test
  public void testDoGetNoCredentials() {
    System.out.println("doGetNoCredentials");
    assertThrows(NotAuthorizedException.class, () -> {target.request(MediaType.TEXT_PLAIN_TYPE)
      .get(String.class);});
  }

}
