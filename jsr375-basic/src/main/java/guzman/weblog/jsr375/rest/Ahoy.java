package guzman.weblog.jaspic.basic.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/")
public class Ahoy {

  @GET
  @RolesAllowed("USER")
  @Produces("text/plain")
  public Response doGet() {
    return Response.ok("method doGet invoked").build();
  }
}
