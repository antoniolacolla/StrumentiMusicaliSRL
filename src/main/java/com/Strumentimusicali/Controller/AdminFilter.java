package com.Strumentimusicali.Controller;

import com.Strumentimusicali.model.Utente;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Utente utente = (Utente) httpRequest.getSession().getAttribute("utenteLoggato");

        if (utente != null && "admin".equals(utente.getRuolo())) {
            // L'utente è un admin, può procedere
            chain.doFilter(request, response);
        } else {
            // L'utente non è un admin o non è loggato, reindirizza al login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}