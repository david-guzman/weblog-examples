package guzman.weblog.jaspic.basic.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;
import static org.testng.Assert.assertEquals;
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

  /**
   * Test of doGet method, of class Ahoy.
   */
  @Test
  public void testDoGet() {
    System.out.println("doGet");
    ClientConfig config = new ClientConfig();
    final Client restClient = ClientBuilder.newClient(config);
    WebTarget target = restClient.target("http://localhost:8081/jaspic-basic-web/rest");
    String result = target.request(MediaType.TEXT_PLAIN_TYPE)
            .header("Authorization", "Basic dGVzdFVzZXI6RXFmOGVU")
            .get(String.class);
    String expResult = "method doGet invoked";
    assertEquals(result, expResult);
  }
  
}
