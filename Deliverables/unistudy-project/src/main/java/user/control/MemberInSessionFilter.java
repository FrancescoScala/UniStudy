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
            // try {
            System.out.println("E quinfi..." + request1.getRequestURL().toString() + "...\n" + request1.getSession(false).getAttribute("memberInSession"));

            if (request1.getSession(false).getAttribute("memberInSession") != null) {
                System.out.println("Utente registrato in sessione: " + request1.getSession(false).getAttribute("memberInSession"));
                chain.doFilter(request, response);
            }
         /*   } catch (NullPointerException e) {
                //e.printStackTrace();
                System.out.println("Sessione non ancora creata. La creo..");
                //request1.getSession(true).getServletContext().getRequestDispatcher("/login.jsp").forward(request1, response);
                HttpServletResponse response1 = (HttpServletResponse) response;
                System.out.println(request1.getContextPath());
                response1.sendRedirect(request1.getContextPath() + "/login.jsp");
            }*/
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
