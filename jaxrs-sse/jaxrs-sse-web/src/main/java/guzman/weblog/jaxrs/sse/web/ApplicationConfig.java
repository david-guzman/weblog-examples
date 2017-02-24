package guzman.weblog.jaxrs.sse.web;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@ApplicationPath("webresources")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);
    return resources;
  }

  /**
   * Do not modify addRestResourceClasses() method.
   * It is automatically populated with
   * all resources defined in the project.
   * If required, comment out calling this method in getClasses().
   */
  private void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(guzman.weblog.jaxrs.sse.web.CorsFilter.class);
    resources.add(guzman.weblog.jaxrs.sse.web.SseAhoy.class);
  }
  
}
