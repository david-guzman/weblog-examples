/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guzman.weblog.jsr375.rest;

import java.nio.charset.Charset;
import static java.util.Arrays.asList;
import java.util.Base64;
import java.util.HashSet;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.glassfish.soteria.test.Utils.notNull;

/**
 *
 * @author david
 */
@ApplicationScoped
public class HeaderAuthenticationMechanism implements HttpAuthenticationMechanism {

    private final String DEF_ENC = "UTF-8";
    
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest hsr, HttpServletResponse hsr1, HttpMessageContext hmc) throws AuthenticationException {
        String header = hsr.getHeader("Authorization");

	if (notNull(header)) {
            
            if (!header.startsWith("Basic ")) {
                return hmc.responseUnauthorized();
            }
            
            header = header.substring(6).trim();
            
            // Decode and parse the authorization header
            byte[] headerBytes = header.getBytes(Charset.forName(DEF_ENC));
            byte[] decBytes = Base64.getDecoder().decode(headerBytes);
            String decoded = new String(decBytes, Charset.forName(DEF_ENC));

            int colon = decoded.indexOf(':');
            if (colon <= 0 || colon == decoded.length() - 1) {
                return hmc.responseUnauthorized();
            }
            
            String name = decoded.substring(0, colon);
            String password = decoded.substring(colon + 1);
            
            UsernamePasswordCredential credential = new UsernamePasswordCredential(name, password);
            CredentialValidationResult res = validate(credential);
            
            if (notNull(name, password)) {
                
                if (res == INVALID_RESULT) {
                    return hmc.responseUnauthorized();
                }
                return hmc.notifyContainerAboutLogin(res);    
            }
		
	}

        return hmc.responseUnauthorized();
    }
    
    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {

        if (usernamePasswordCredential.compareTo("testUser", "testPassword")) {
            return new CredentialValidationResult("testUser", new HashSet<>(asList("USER")));
        }

        return INVALID_RESULT;
    }
    
}
