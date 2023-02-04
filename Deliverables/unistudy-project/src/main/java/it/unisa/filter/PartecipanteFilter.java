package it.unisa.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
@WebFilter(urlPatterns = "/partecipante/*", dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class PartecipanteFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Chiamata a /partecipante/*");
        System.out.println("Utente in sessione: "+ ((HttpServletRequest)request).getSession().getAttribute("userInSession"));
        chain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
