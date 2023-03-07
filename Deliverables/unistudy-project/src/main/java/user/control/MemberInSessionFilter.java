package user.control;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "MemberInSessionFilter", urlPatterns = "/*")
public class MemberInSessionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        if (request1.getRequestURI().startsWith(request1.getContextPath() + "/user/login.jsp") ||
                request1.getRequestURI().startsWith(request1.getContextPath() + "/user/signup.jsp") ||
                request1.getRequestURI().equals(request1.getContextPath() + "/") ||
                request1.getRequestURI().startsWith(request1.getContextPath() + "/assets")) {
            System.out.println("Sono in signup o nella pagina iniziale o in login jsp " + request1.getRequestURL().toString());
            chain.doFilter(request, response);
        } else if (request1.getRequestURI().startsWith(request1.getContextPath() + "/user/MemberControl")) {
            if (request1.getParameter("action").equals("signup") || request1.getParameter("action").equals("login")) {
                System.out.println("Sono in MemberControl signup o login");
                chain.doFilter(request, response);
            } else {
                //pagina di errore
                System.out.println("LOGOUT..." + request1.getRequestURL().toString() + "...\n" + request1.getSession(false).getAttribute("memberInSession"));

                if (request1.getSession(false).getAttribute("memberInSession") != null) {
                    System.out.println("Utente registrato in sessione: " + request1.getSession(false).getAttribute("memberInSession"));
                    chain.doFilter(request, response);
                }
            }
        } else {
            //pagina di errore
            try {
                System.out.println("E quinfi..." + request1.getRequestURL().toString() + "...\n" + request1.getSession(false).getAttribute("memberInSession"));
                if (request1.getSession(false).getAttribute("memberInSession") != null) {
                    System.out.println("Utente registrato in sessione: " + request1.getSession(false).getAttribute("memberInSession"));
                    chain.doFilter(request, response);
                }
                // analizzare l'utilizzo di una funzione per eliminare ridondanza tra le azioni del blocco else sottostante e il catch dell'eccezione
                else {
                    redirectToLogin(request1, response1);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                redirectToLogin(request1, response1);
            }
        }
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Sessione non ancora creata. La creo..");
        System.out.println(request.getContextPath());
        response.sendRedirect(request.getContextPath() + "/user/login.jsp");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
