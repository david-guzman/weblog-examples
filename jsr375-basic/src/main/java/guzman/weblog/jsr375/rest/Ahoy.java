package guzman.weblog.jsr375.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.security.enterprise.SecurityContext;


@Path("/")
public class Ahoy {

  @Inject
  private SecurityContext securityContext;

  @GET
  @Produces("text/plain")
  public Response doGet() {

    if (securityContext.getCallerPrincipal() != null) {
      return Response.ok(securityContext.getCallerPrincipal().getName()).build();
    }

    return Response.ok("method doGet invoked").build();
  }
}
