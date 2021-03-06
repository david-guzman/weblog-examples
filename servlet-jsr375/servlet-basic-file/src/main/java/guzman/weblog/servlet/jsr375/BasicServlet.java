package guzman.weblog.servlet.jsr375;

import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/basicServlet")
@DeclareRoles({"USER"})
@BasicAuthenticationMechanismDefinition(realmName = "www-authenticate-realm")
@ServletSecurity(@HttpConstraint(rolesAllowed = "USER"))
public class BasicServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String webName = null;
    if (req.getUserPrincipal() != null) {
      webName = req.getUserPrincipal().getName();
    }

    resp.getWriter().write("Caller " + webName + " in role USER");
  }
}
