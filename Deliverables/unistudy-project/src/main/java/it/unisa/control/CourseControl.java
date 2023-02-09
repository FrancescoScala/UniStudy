package it.unisa.control;

import it.unisa.beans.Course;
import it.unisa.dao.CourseManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

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
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                boolean created = CourseManager.createCourse(request.getParameter("professors"),request.getParameter("schedule"),request.getParameter("title"));
                String mex;
                if(created) {
                    mex = "OK";
                }
                else {
                    mex = "Aggiunta fallita. Inserisci i dati nel formato corretto";
                }
                JSONObject json = new JSONObject();
                json.put("result",mex);
                out.print(json.toString());
                break;

            case "view":
                if(request.getParameter("qty").equals("all")) {
                    response.setContentType("application/json");
                    PrintWriter out1 = response.getWriter();
                    JSONObject json1 = new JSONObject();
                    json1.put("objects", CourseManager.retrieveAll());
                    System.out.println(json1.toString());
                    out1.print(json1.toString());
                }
                else {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Course course = CourseManager.retrieveCourseById(id);
                    request.setAttribute("course",course);
                    request.getRequestDispatcher("/partecipante/corso/view_course.jsp?id="+course.getId()+"").forward(request,response);
                }
                break;

            case "delete":
                response.setContentType("application/json");
                PrintWriter out2 = response.getWriter();
                boolean deleted = CourseManager.deleteCourse(Integer.parseInt(request.getParameter("id")));
                if(deleted)
                    mex = "OK";
                else
                    mex = "Errore nella rimozione del corso. Riprovare.";
                JSONObject json2 = new JSONObject();
                json2.put("result",mex);
                out2.print(json2.toString());
                break;

            default:
                // pagina 404
        }
    }
}
