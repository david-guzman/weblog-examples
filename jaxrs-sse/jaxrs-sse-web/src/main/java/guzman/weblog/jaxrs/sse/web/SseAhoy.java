package guzman.weblog.jaxrs.sse.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
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

  private final List<ClinicEvent> eventsList = new ArrayList<>();
  
  /**
   * Creates a new instance of SseAhoy
   */
  public SseAhoy() {
    eventsList.add(new ClinicEvent(ClinicTeam.NURSE, "Patient 97649142"));
    eventsList.add(new ClinicEvent(ClinicTeam.REGISTRAR1, "Patient 97649142"));
    eventsList.add(new ClinicEvent(ClinicTeam.NURSE, "Patient 04703214"));
    eventsList.add(new ClinicEvent(ClinicTeam.REGISTRAR2, "Patient 04703214"));
    eventsList.add(new ClinicEvent(ClinicTeam.PHLEBOTOMIST, "Patient 97649142"));
    eventsList.add(new ClinicEvent(ClinicTeam.NURSE, "Patient 98659342"));
    eventsList.add(new ClinicEvent(ClinicTeam.CONSULTANT, "Patient 98659342"));
    eventsList.add(new ClinicEvent(ClinicTeam.NURSE, "Patient 97649142"));
    eventsList.add(new ClinicEvent(ClinicTeam.RADIOLOGIST, "Patient 04703214"));
    eventsList.add(new ClinicEvent(ClinicTeam.PHLEBOTOMIST, "Patient 04703214"));
    eventsList.add(new ClinicEvent(ClinicTeam.PHLEBOTOMIST, "Patient 98659342"));
  }

  @GET
  @Produces(SseFeature.SERVER_SENT_EVENTS)
  public EventOutput getServerSentEvents() {
    final EventOutput eventOutput = new EventOutput();
    
    Runnable eventsTask = () -> {
      try {
        Iterator<ClinicEvent> events = eventsList.iterator();
        
        for (ClinicEvent clinicEvent : eventsList) {
          final OutboundEvent.Builder eventBuilder
                  = new OutboundEvent.Builder();
          eventBuilder.name(clinicEvent.getRole().name());
          eventBuilder.mediaType(MediaType.TEXT_PLAIN_TYPE);
          eventBuilder.data(String.class, clinicEvent.getData());
          final OutboundEvent event = eventBuilder.build();
          eventOutput.write(event);
          TimeUnit.SECONDS.sleep(1);
        }
        
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
  
  public class ClinicEvent {
    private final ClinicTeam role;
    private final String data;
    
    ClinicEvent(ClinicTeam role, String data) {
      this.role = role;
      this.data = data;
    }
    
    public ClinicTeam getRole() {
      return role;
    }
    
    public String getData() {
      return data;
    }
  }
  
  public enum ClinicTeam {
    
    NURSE,
    REGISTRAR1,
    REGISTRAR2,
    CONSULTANT,
    RADIOLOGIST,
    PHLEBOTOMIST;

    public static boolean contains(String str) {
      return Arrays.stream(ClinicTeam.values())
              .anyMatch((ClinicTeam t) -> t.name().equals(str));
    }
  }
}
