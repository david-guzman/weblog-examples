/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guzman.weblog.jaxrs.sse.web;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * REST Web Service
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Path("sse")
public class SseAhoy {

  private final String patientPathway = "CHECK-IN NURSE DOCTOR FLEBOTOMIST RADIOLOGIST PHARMACIST";
  private final Scanner pathwayScanner = new Scanner(patientPathway);

  /**
   * Creates a new instance of SseAhoy
   */
  public SseAhoy() {
  }

  @GET
  @Produces(SseFeature.SERVER_SENT_EVENTS)
  public EventOutput getServerSentEvents() {
    final EventOutput eventOutput = new EventOutput();
    
    Runnable eventsTask = () -> {
      try {
        while (pathwayScanner.hasNext()) {
          final OutboundEvent.Builder eventBuilder
                  = new OutboundEvent.Builder();
          eventBuilder.name("message-to-client");
          eventBuilder.data(String.class,
                  "Task " + pathwayScanner.next() );
          final OutboundEvent event = eventBuilder.build();
          eventOutput.write(event);
          TimeUnit.SECONDS.sleep(1);
        }
//        for (int i = 0; i < 10; i++) {
//          TimeUnit.SECONDS.sleep(1);
//          final OutboundEvent.Builder eventBuilder
//                  = new OutboundEvent.Builder();
//          eventBuilder.name("message-to-client");
//          eventBuilder.data(String.class,
//                  "Ahoy " + i + "!");
//          final OutboundEvent event = eventBuilder.build();
//          eventOutput.write(event);
//        }
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(
                "Error when writing the event.", e);
      } finally {
        try {
          eventOutput.close();
        } catch (IOException ioClose) {
          throw new RuntimeException(
                  "Error when closing the event output.", ioClose);
        }
      }
    };
    
    ExecutorService taskExecutor = Executors.newSingleThreadExecutor();
    
    taskExecutor.execute(eventsTask);
    taskExecutor.shutdown();
    
    return eventOutput;
  }
}
