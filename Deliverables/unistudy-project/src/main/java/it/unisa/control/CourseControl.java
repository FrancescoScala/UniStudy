package it.unisa.control;

import it.unisa.beans.Course;
import it.unisa.beans.Enrollment;
import it.unisa.beans.User;
import it.unisa.dao.CourseManager;
import it.unisa.dao.EnrollmentManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "CourseControl", value = "/CourseControl")
public class CourseControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getParameter("action")) {
            case "create":
                System.out.println("Creo il corso");
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                System.out.println("Parametri passati: "+request.getParameter("professors")+request.getParameter("schedule")+request.getParameter("title"));
                boolean created = CourseManager.createCourse(request.getParameter("professors"), request.getParameter("schedule"), request.getParameter("title"));
                String mex;
                if (created) {
                    mex = "OK";
                } else {
                    mex = "Aggiunta fallita. Inserisci i dati nel formato corretto";
                }
                JSONObject json = new JSONObject();
                json.put("result", mex);
                out.print(json.toString());
                break;

            case "view":
                if (request.getParameter("qty").equals("all")) {
                    response.setContentType("application/json");
                    PrintWriter out1 = response.getWriter();
                    JSONObject json1 = new JSONObject();
                    json1.put("objects", CourseManager.retrieveAll());
                    System.out.println(json1.toString());
                    out1.print(json1.toString());
                }
                else if(request.getParameter("qty").equals("all-objects")){
                    Set<Course> courses = CourseManager.retrieveAll();
                    request.setAttribute("courses", courses);
                    request.getRequestDispatcher("/partecipante/all-courses.jsp").forward(request,response);
                }
                else if (request.getParameter("qty").equals("one")) {
                    response.setContentType("application/json");
                    PrintWriter out1 = response.getWriter();
                    JSONObject json1 = new JSONObject();
                    Set<Course> course = new HashSet<>();
                    course.add(CourseManager.retrieveCourseById(Integer.parseInt(request.getParameter("id"))));
                    json1.put("course", course);
                    System.out.println(json1.toString());
                    out1.print(json1.toString());
                } else {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Course course = CourseManager.retrieveCourseById(id);
                    request.setAttribute("course", course);
                    request.getRequestDispatcher("/partecipante/corso/view_course.jsp?id=" + course.getId() + "").forward(request, response);
                }
                break;

            case "delete":
                response.setContentType("application/json");
                PrintWriter out2 = response.getWriter();
                boolean deleted = CourseManager.deleteCourse(Integer.parseInt(request.getParameter("id")));
                if (deleted)
                    mex = "OK";
                else
                    mex = "Errore nella rimozione del corso. Riprovare.";
                JSONObject json2 = new JSONObject();
                json2.put("result", mex);
                out2.print(json2.toString());
                break;

            case "enroll":
                int userId =((User) request.getSession().getAttribute("userInSession")).getId();
                Enrollment enrollment = EnrollmentManager.createEnrollment(userId,Integer.parseInt(request.getParameter("id")),
                        Enrollment.EnrollType.STUDENTE,request.getParameter("title"));

                Set<Enrollment> enrollments = (Set<Enrollment>) request.getSession().getAttribute("enrollments");
                enrollments.add(enrollment);
                request.getSession().setAttribute("enrollments",enrollments);
                Course course = CourseManager.retrieveCourseById(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("course", course);
                request.getRequestDispatcher("/partecipante/corso/view_course.jsp?id=" +
                        request.getParameter("id") + "").forward(request, response);
                break;

            case "delete-enroll":
                System.out.println("Sono in delete enrolls");
                User user = (User) request.getSession().getAttribute("userInSession");
                Set<Enrollment> enrollmentsSet = (Set<Enrollment>) request.getSession().getAttribute("enrollments");
                System.out.println(enrollmentsSet);
                int courseId = Integer.parseInt(request.getParameter("id"));
                System.out.println("courseId: "+courseId);
                boolean isGestore = false; // da gestire

                for(Enrollment e : enrollmentsSet)
                {
                    if(e.getCourseId() == courseId) {
                        for (Enrollment.EnrollType enrollType : e.getRoles()) {
                            if (enrollType.toString().equals("GESTORECORSO")) {
                                System.out.println("trovato gestore");
                                isGestore = true; //non funziona
                            }
                        }
                    }
                }
                System.out.println(isGestore);
                boolean check1 = EnrollmentManager.deleteEnrollment(user.getId(),courseId,isGestore);
                if(check1)
                {
                    request.getSession().setAttribute("enrollments",EnrollmentManager.retrieveEnrollmentsByUserId(user.getId()));
                    request.getRequestDispatcher("/partecipante/homepage.jsp").forward(request, response);
                }
                // else pagina errore
                break;

            case "modify":
                System.out.println("Sono in modify info course");
                response.setContentType("application/json");
                PrintWriter out3 = response.getWriter();
                System.out.println("Eseguo la retrieve su courseId: "+request.getParameter("id"));
                Course course1 = CourseManager.retrieveCourseById(Integer.parseInt(request.getParameter("id")));
                System.out.println(course1+request.getParameter("schedule")+request.getParameter("professors"));
                boolean check = CourseManager.modifyInfoCourse(course1,
                        request.getParameter("professors"),
                        request.getParameter("schedule"));
                if (true)
                    mex = "OK";
                else
                    mex = "Errore nella modifica delle info del corso. Riprovare.";
                JSONObject json3 = new JSONObject();
                System.out.println(mex);
                json3.put("result", mex);
                out3.print(json3.toString());
                break;
            default:
                // pagina 404
        }
    }
}
