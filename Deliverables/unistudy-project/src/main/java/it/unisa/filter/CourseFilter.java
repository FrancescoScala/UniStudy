package it.unisa.filter;

import it.unisa.beans.Enrollment;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter(urlPatterns = "/partecipante/corso/*", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class CourseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        Set<Enrollment> enrollments = (Set<Enrollment>) request1.getSession(false).getAttribute("enrollments");
        int id = Integer.parseInt(request1.getParameter("id"));

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == id) {
                for (Enrollment.EnrollType role : enrollment.getRoles()) {
                    if(request1.getRequestURI().startsWith(request1.getContextPath()+"/partecipante/corso/gestore")) {
                        if ((role.toString()).equals("GESTORECORSO")) {
                            chain.doFilter(request, response);
                            return;
                        }
                    }
                    else
                    {
                        if ((role.toString()).equals("STUDENTE")) {
                            chain.doFilter(request, response);
                            return;
                        }
                    }
                }
            }
        }

        HttpServletResponse response1 = (HttpServletResponse) response;
        response1.sendRedirect(request1.getContextPath()+"/partecipante/enroll.jsp?id="+id+"&title="+request1.getParameter("courseTitle"));
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }


}
