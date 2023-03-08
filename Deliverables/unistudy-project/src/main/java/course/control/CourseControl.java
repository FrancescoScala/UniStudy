package course.control;

import course.beans.Course;
import course.beans.Enrollment;
import user.beans.Member;
import course.manager.CourseManager;
import course.manager.EnrollmentManager;
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
                System.out.println("Parametri passati: " + request.getParameter("professors") + request.getParameter("schedule") + request.getParameter("title"));
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
                System.out.println("Vedo il corso");
                if (request.getParameter("qty").equals("all")) {
                    response.setContentType("application/json");
                    PrintWriter out1 = response.getWriter();
                    JSONObject json1 = new JSONObject();
                    json1.put("objects", CourseManager.retrieveAll());
                    System.out.println(json1.toString());
                    out1.print(json1.toString());
                } else if (request.getParameter("qty").equals("all-objects")) {
                    Set<Course> courses = CourseManager.retrieveAll();
                    request.setAttribute("courses", courses);
                    request.getRequestDispatcher("/course/all-courses.jsp").forward(request, response);
                } else if (request.getParameter("qty").equals("one")) {
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
                    request.getRequestDispatcher("/course/studente/view_course.jsp?id=" + course.getId() + "").forward(request, response);
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
                int memberId = ((Member) request.getSession().getAttribute("memberInSession")).getId();
                int courseIdParam = Integer.parseInt(request.getParameter("id"));
                Set<Enrollment> enrollments = (Set<Enrollment>) request.getSession().getAttribute("enrollments");
                boolean isStudente = false;
                Enrollment addedEnrollment = null;

                for (Enrollment enrollment : enrollments) {
                    if (enrollment.getCourseId() == courseIdParam) {
                        addedEnrollment = EnrollmentManager.updateEnrollment(enrollment, Enrollment.EnrollType.STUDENTE);
                        isStudente = true;
                        break;
                    }
                }

                if (!isStudente) {
                    addedEnrollment = EnrollmentManager.createEnrollment(memberId, courseIdParam,
                            Enrollment.EnrollType.STUDENTE, request.getParameter("title"));
                    enrollments.add(addedEnrollment);
                }

                request.getSession().setAttribute("enrollments", enrollments);
                Course course = CourseManager.retrieveCourseById(courseIdParam);
                request.setAttribute("course", course);
                request.getRequestDispatcher("/course/studente/view_course.jsp?id=" +
                        courseIdParam + "").forward(request, response);
                break;

            case "delete-enroll":
                Member member = (Member) request.getSession().getAttribute("memberInSession");
                Set<Enrollment> enrollmentsSet = (Set<Enrollment>) request.getSession().getAttribute("enrollments");
                int courseId = Integer.parseInt(request.getParameter("id"));
                boolean isGestore = false;

                for (Enrollment e : enrollmentsSet) {
                    if (e.getCourseId() == courseId) {
                        for (Enrollment.EnrollType enrollType : e.getRoles()) {
                            if (enrollType.toString().equals("GESTORECORSO")) {
                                isGestore = true;
                            }
                        }
                    }
                }
                boolean check1 = EnrollmentManager.deleteEnrollment(member.getId(), courseId, isGestore);
                if (check1) {
                    request.getSession().setAttribute("enrollments", EnrollmentManager.retrieveEnrollmentsByMemberId(member.getId()));
                    request.getRequestDispatcher("/course/homepage.jsp").forward(request, response);
                }
                // else pagina errore
                break;

            case "modify":
                System.out.println("Sono in modify info course");
                response.setContentType("application/json");
                PrintWriter out3 = response.getWriter();
                System.out.println("Eseguo la retrieve su courseId: " + request.getParameter("id"));
                Course course1 = CourseManager.retrieveCourseById(Integer.parseInt(request.getParameter("id")));
                System.out.println(course1 + request.getParameter("schedule") + request.getParameter("professors"));
                boolean check = CourseManager.modifyInfoCourse(course1,
                        request.getParameter("professors"),
                        request.getParameter("schedule"));
                if (check)
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
