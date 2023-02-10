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
                System.out.println("SONO NELLO SWITCH iN add\n"+"Titolo: "+request.getParameter("titolo")+" action: "+request.getParameter("action")+
                        "Id: "+ request.getParameter("id")+ "descrizione: "+ request.getParameter("description"));
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                Course course = CourseManager.retrieveCourseById(Integer.parseInt(request.getParameter("id")));
                System.out.println(course);
                boolean check = NoticeManager.createNotice(request.getParameter("title"),
                        new Timestamp(System.currentTimeMillis()),
                        request.getParameter("description"),
                        course); // forse inutile...

                JSONObject json = new JSONObject();
                String mex;
                if(check) {
                    mex="OK";
                }
                else {
                    mex="AVVISO NON CREATO VERIFICA CHE IL FORMATO DEI DATI SIA CORRETTO";
                }
                json.put("result",mex);
                System.out.println("signup restituisce: "+mex);
                out.print(json.toString());

                break;
            case "delete":

                break;
            default:
                // pagina 404
        }
    }
}
