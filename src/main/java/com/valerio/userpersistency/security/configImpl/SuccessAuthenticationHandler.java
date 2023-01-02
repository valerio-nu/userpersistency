package com.valerio.userpersistency.security.configImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component("authenticationSuccessHandler")
public class SuccessAuthenticationHandler  implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(SuccessAuthenticationHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String targetUrl = null;
        Supplier<Stream<? extends GrantedAuthority>> sup = () -> authentication.getAuthorities().stream();

        if(sup.get().anyMatch(a->(((GrantedAuthority) a).getAuthority().equals("ROLE_ADMIN")))) {
            targetUrl = "/persistence/homepage/Dashboard";
        } else if(sup.get().anyMatch(a->(((GrantedAuthority) a).getAuthority().equals("ROLE_USER")))) {
            targetUrl = "/persistence/homepage/user";
        } else {
            targetUrl = "/persistence";
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);

        final HttpSession session = request.getSession(false);
        if (session != null) {
            // inactive interval in minutes between client requests before the servlet container will invalidate this session
            session.setMaxInactiveInterval(10 * 60);
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
