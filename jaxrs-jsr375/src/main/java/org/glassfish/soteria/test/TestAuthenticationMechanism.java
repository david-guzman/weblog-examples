/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015, 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.soteria.test;

import java.nio.charset.Charset;
import static java.util.Arrays.asList;
import java.util.Base64;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static org.glassfish.soteria.test.Utils.notNull;

import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class TestAuthenticationMechanism implements HttpAuthenticationMechanism {
    
    private final String DEF_ENC = "UTF-8";

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {

	String header = request.getHeader("Authorization");

	if (notNull(header)) {
            
            System.out.println("Obtaining credentials from header " + header);
            
            if (!header.startsWith("Basic ")) {
                return httpMessageContext.responseUnauthorized();
            }
            
            header = header.substring(6).trim();
            
            // Decode and parse the authorization header
            byte[] headerBytes = header.getBytes(Charset.forName(DEF_ENC));
            byte[] decBytes = Base64.getDecoder().decode(headerBytes);
            String decoded = new String(decBytes, Charset.forName(DEF_ENC));

            int colon = decoded.indexOf(':');
            if (colon <= 0 || colon == decoded.length() - 1) {
                return httpMessageContext.responseUnauthorized();
            }
            
            String name = decoded.substring(0, colon);
            String password = decoded.substring(colon + 1);
            
            System.out.println("compare reza" + "reza".compareTo(name));
            System.out.println("compare secret1" + "secret1".compareTo(password));
            
            if (notNull(name, password)) {
            return httpMessageContext.notifyContainerAboutLogin(
                validate(new UsernamePasswordCredential(name, password)));    
            }
		
	}

        return httpMessageContext.doNothing();
    }
    
    public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {

        if (usernamePasswordCredential.compareTo("reza", "secret1")) {
            return new CredentialValidationResult("reza", new HashSet<>(asList("foo", "bar", "USER")));
        }

        return INVALID_RESULT;
    }
    
}