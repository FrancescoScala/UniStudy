package it.unisa.control;

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

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getParameter("action")) {
            case "create":
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                boolean check = CourseManager.createCourse(request.getParameter("professors"),request.getParameter("schedule"),request.getParameter("title"));
                String mex;
                if(check) {
                    mex = "OK";
                }
                else {
                    mex = "Aggiunta fallita. Inserisci i dati nel formato corretto";
                }
                JSONObject json = new JSONObject();
                json.put("result",mex);
                out.print(json.toString());
                break;

            case "delete":
           //     response.setContentType("application/json");
             //   PrintWriter out1 = response.getWriter();

                break;

            case "view":
                response.setContentType("application/json");
                PrintWriter out1 = response.getWriter();
                JSONObject json1 = new JSONObject();
                json1.put("all",CourseManager.retrieveAll());
                System.out.println(json1.toString());
            //    out1.print(json1.toString());
                break;

            default:
                // pagina 404
        }
    }
}
