package guzman.weblog.jsr375.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;


@ApplicationPath("rest")
@DeclareRoles({"USER"})
public class ApplicationConfig extends ResourceConfig {

  public ApplicationConfig() {
    super(Ahoy.class);
    register(RolesAllowedDynamicFeature.class);
  }

}
