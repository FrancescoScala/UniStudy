package course.control;

import course.beans.Enrollment;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter(filterName = "CourseFilter", urlPatterns = {"/course/studente/*", "/course/gestorecorso/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class CourseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("SONO IN COURSE FILTER");
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;

        Set<Enrollment> enrollments = (Set<Enrollment>) request1.getSession(false).getAttribute("enrollments");
        System.out.println("Enrollments registrati in sessione " + enrollments);
        int id = Integer.parseInt(request1.getParameter("id"));

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == id) {
                System.out.println("HAI TROVATO IL CORSO (quindi sei iscritto)");
                for (Enrollment.EnrollType role : enrollment.getRoles()) {
                    //       System.out.println("SONO "+ role +" Cerco"+ request1.getParameter("role"));
                    if (request1.getRequestURI().startsWith(request1.getContextPath() + "/course/gestorecorso")) {
                        if ((role.toString()).equals("GESTORECORSO")) {
                            //            System.out.println("Hai il permesso " + request1.getParameter("role"));
                            chain.doFilter(request, response);
                            return;
                        }
                    } else if (request1.getRequestURI().startsWith(request1.getContextPath() + "/course/studente")){
                        if ((role.toString()).equals("STUDENTE")) {
                            //            System.out.println("Hai il permesso " + request1.getParameter("role"));
                            chain.doFilter(request, response);
                            return;
                        }
                    }//else?
                }
            }
        }
        //    System.out.println("NON SEI ISCRITTO AL CORSO");


    /*    RequestDispatcher dispatcher = request1.getRequestDispatcher("/partecipante/enroll.jsp?id="+id+"&title="+request1.getParameter("courseTitle"));
        request1.setAttribute("courseTitle",request1.getParameter("courseTitle"));

        dispatcher.forward(request1,response1);*/
        response1.sendRedirect(request1.getContextPath() + "/course/enroll.jsp?id=" + id + "&title=" + request1.getParameter("courseTitle"));
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
