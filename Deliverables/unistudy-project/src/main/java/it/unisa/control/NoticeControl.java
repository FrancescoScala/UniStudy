package it.unisa.control;

import it.unisa.beans.Course;
import it.unisa.dao.CourseManager;
import it.unisa.dao.NoticeManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

@WebServlet(name = "NoticeControl", value = "/NoticeControl")
public class NoticeControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getParameter("action")) {
            case "add":
                Course course = CourseManager.retrieveCourseById(Integer.parseInt(request.getParameter("id")));
                NoticeManager.createNotice(request.getParameter("title"),
                        new Timestamp(System.currentTimeMillis()),
                        request.getParameter("description"),
                        course); // forse inutile...
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/partecipante/corso/view_course.jsp?id="+course.getId());
                request.setAttribute("course",course); // forse inutile
                dispatcher.forward(request,response);
                break;
            case "delete":

                break;
            default:
                // pagina 404
        }
    }
}
