package guzman.weblog.jsr375.rest;

import org.glassfish.soteria.identitystores.annotation.Credentials;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
@DeclareRoles({"USER"})
@BasicAuthenticationMechanismDefinition(realmName = "embedded")
@EmbeddedIdentityStoreDefinition({@Credentials(callerName = "testUser", password = "testPassword", groups = {"USER"})})
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new HashSet<>();
    resources.add(Ahoy.class);
    return resources;
  }
}
