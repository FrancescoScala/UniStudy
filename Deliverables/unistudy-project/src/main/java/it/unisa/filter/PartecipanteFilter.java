package it.unisa.filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "PartecipanteFilter",urlPatterns = "/partecipante/*", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class PartecipanteFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        try {
            System.out.println(request1.getSession(false).getAttribute("userInSession"));
            if (request1.getSession(false).getAttribute("userInSession") != null) {
                System.out.println("Utente registrato in sessione");
                chain.doFilter(request, response);
            }
        }

        catch(NullPointerException e)
        {
            e.printStackTrace();
            System.out.println("Sessione non ancora creata. La creo..");
            //request1.getSession(true).getServletContext().getRequestDispatcher("/login.jsp").forward(request1, response);
            HttpServletResponse response1 = (HttpServletResponse) response;
            System.out.println(request1.getContextPath());
            response1.sendRedirect(request1.getContextPath()+"/login.jsp");
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
