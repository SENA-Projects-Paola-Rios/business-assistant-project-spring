package com.sena.BusinessAssistantSpring.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

	//uso un filtro para que cada que se este cargando una pagina se verifique si el usuario esta autenticado
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        boolean isLoggedIn = session != null && session.getAttribute("loggedUser") != null;
        boolean isLoginRequest = uri.endsWith("/login") || uri.contains("/css") || uri.contains("/js") || uri.contains("/img");

        if (isLoggedIn || isLoginRequest) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
