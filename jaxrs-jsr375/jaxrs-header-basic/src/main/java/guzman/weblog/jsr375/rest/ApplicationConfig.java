package guzman.weblog.jsr375.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.soteria.identitystores.annotation.Credentials;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.ws.rs.ApplicationPath;


@ApplicationScoped
@ApplicationPath("rest")
@DeclareRoles({"USER"})
@BasicAuthenticationMechanismDefinition(realmName = "embedded")
@EmbeddedIdentityStoreDefinition({@Credentials(callerName = "testUser", password = "testPassword", groups = {"USER"})})
public class ApplicationConfig extends ResourceConfig {

  public ApplicationConfig() {
    super(Ahoy.class);
    register(RolesAllowedDynamicFeature.class);
  }

}
