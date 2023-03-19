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
                request1.getRequestURI().startsWith(request1.getContextPath() + "/assets") ||
                request1.getRequestURI().startsWith(request1.getContextPath() + "/user/assets")) {
            chain.doFilter(request, response);
        } else if (request1.getRequestURI().startsWith(request1.getContextPath() + "/user/MemberControl")) {
            if (request1.getParameter("action").equals("signup") || request1.getParameter("action").equals("login")) {
                chain.doFilter(request, response);
            } else {
                isInSession(request1, response1, chain);
            }
        } else {
            isInSession(request1, response1, chain);
        }
    }

    private void isInSession(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            if (request.getSession(false).getAttribute("memberInSession") != null) {
                chain.doFilter(request, response);
            }
            else{
                response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
