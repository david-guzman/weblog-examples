package guzman.weblog.jaspic.basic.rest;

import java.io.File;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
public class AhoyIT extends Arquillian {
  
  private static final String WEBAPP_SRC = "src/main/webapp";
  
  @ArquillianResource
  private URL deploymentUrl;
  
  public AhoyIT() {
  }
  
  @Deployment(testable=false)
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class,"test.war")
            .addClasses(Ahoy.class, RestApplication.class)
            .setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
  }

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
    final Client restClient = ClientBuilder.newClient();
    String result = restClient.target(deploymentUrl.toExternalForm() + "rest")
            .request("text/plain")
            .get(String.class);
    String expResult = "method doGet invoked";
    assertEquals(result, expResult);
  }
  
}
