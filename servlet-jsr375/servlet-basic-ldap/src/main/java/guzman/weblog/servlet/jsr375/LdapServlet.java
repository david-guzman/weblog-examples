package guzman.weblog.servlet.jsr375;

import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = "/ldapServlet")
@DeclareRoles({"USER"})
@LdapIdentityStoreDefinition
@ServletSecurity(@HttpConstraint(rolesAllowed = "USER"))
public class LdapServlet extends HttpServlet {

}
