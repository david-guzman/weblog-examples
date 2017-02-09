package guzman.weblog.jaspic.basic.rest;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class AhoyIT {
  
  public AhoyIT() {}

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }

  @Test
  public void testDoGetValidCredentials() {
    System.out.println("doGetValidCredentials");
    ClientConfig config = new ClientConfig();
    final Client restClient = ClientBuilder.newClient(config);
    WebTarget target = restClient.target("http://localhost:8081/jaspic-basic-web/rest");
    String result = target.request(MediaType.TEXT_PLAIN_TYPE)
            .header("Authorization", "Basic dGVzdFVzZXI6dGVzdFBhc3N3b3Jk")
            .get(String.class);
    String expResult = "method doGet invoked";
    assertEquals(result, expResult);
  }
  
  @Test
  public void testDoGetInvalidPassword() {
    System.out.println("doGetInvalidPassword");
    ClientConfig config = new ClientConfig();
    final Client restClient = ClientBuilder.newClient(config);
    WebTarget target = restClient.target("http://localhost:8081/jaspic-basic-web/rest");
    assertThrows(ForbiddenException.class, () -> {target.request(MediaType.TEXT_PLAIN_TYPE)
            .header("Authorization", "Basic dGVzdFVzZXI6aW52YWxpZFBhc3N3b3Jk")
            .get(String.class);});
  }
  
  @Test
  public void testDoGetNoCredentials() {
    System.out.println("doGetNoCredentials");
    ClientConfig config = new ClientConfig();
    final Client restClient = ClientBuilder.newClient(config);
    WebTarget target = restClient.target("http://localhost:8081/jaspic-basic-web/rest");
    assertThrows(ForbiddenException.class, () -> {target.request(MediaType.TEXT_PLAIN_TYPE)
            .get(String.class);});
  }
  
}
