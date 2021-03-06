package guzman.weblog.jaxrs.sse.web;

import java.util.regex.Pattern;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class SseAhoyIT {
  
  private final String messagePattern = "^(.*)\\s:\\sPatient\\s\\d{8}$";
   
  public SseAhoyIT() {
  }

  @Test
  public void testGetServerSentEvents() {
    System.out.println("testGetServerSentEvents()");
    Client client = ClientBuilder
            .newBuilder()
            .register(SseFeature.class)
            .build();
    WebTarget target = client
            .target("http://localhost:8081/jaxrs-sse-web/webresources/sse");

    EventInput eventInput = target
            .request()
            .get(EventInput.class);
    while (!eventInput.isClosed()) {
      final InboundEvent inboundEvent = eventInput.read();
      if (inboundEvent == null) {
        break;
      }
      assertEquals(inboundEvent.getName(), "attendance-list");
      assertTrue(Pattern.matches(messagePattern, inboundEvent.readData(String.class)));
      System.out.println(inboundEvent.getName() + ": " + inboundEvent.readData(String.class));
    }

  }

}
