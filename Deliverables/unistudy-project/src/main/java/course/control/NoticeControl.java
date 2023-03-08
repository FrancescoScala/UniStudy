package course.control;

import course.beans.Notice;
import course.manager.NoticeManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Set;

@WebServlet(name = "NoticeControl", value = "/NoticeControl")
public class NoticeControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getParameter("action")) {
            case "add":
                System.out.println("SONO NELLO SWITCH iN add\n"+"Titolo: "+request.getParameter("titolo")+" action: "+request.getParameter("action")+
                        "Id: "+ request.getParameter("id")+ "descrizione: "+ request.getParameter("description"));
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                boolean check = NoticeManager.createNotice(request.getParameter("title"),
                        new Timestamp(System.currentTimeMillis()),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("id"))); // forse inutile...MA ORA RISOLTO?

                JSONObject json = new JSONObject();
                String mex;
                if(check) {
                    mex="OK";
                }
                else {
                    mex="AVVISO NON CREATO VERIFICA CHE IL FORMATO DEI DATI SIA CORRETTO";
                }
                json.put("result",mex);
                System.out.println("CreateNotice: action=add restituisce: "+mex);
                out.print(json.toString());

                break;

            case "view":
                Set<Notice> notices = NoticeManager.retrieveNoticesByCourseId(Integer.parseInt(request.getParameter("id")));
                System.out.println(notices);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/course/gestorecorso/remove-alert.jsp?id="+request.getParameter("id"));
                request.setAttribute("notices", notices);
                dispatcher.forward(request,response);
                break;

            case "delete":
                boolean checkDelete = NoticeManager.deleteNotice(Integer.parseInt(request.getParameter("noticeId")));
                if(checkDelete)
                {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Set<Notice> notices1 = NoticeManager.retrieveNoticesByCourseId(id);
                    RequestDispatcher dispatcher1 = this.getServletContext().getRequestDispatcher("/course/gestorecorso/remove-alert.jsp?id="+id);
                    request.setAttribute("notices", notices1);
                    dispatcher1.forward(request,response);
                }
                break;

            default:
                // pagina 404
        }
    }
}
