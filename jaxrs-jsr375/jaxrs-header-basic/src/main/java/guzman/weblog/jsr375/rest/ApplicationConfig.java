package guzman.weblog.jsr375.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class ApplicationConfig extends Application {

  //@Override
  //public Set<Class<?>> getClasses() {
  //  Set<Class<?>> resources = new HashSet<>();
  //  resources.add(Ahoy.class);
  //  return resources;
  //}
}
