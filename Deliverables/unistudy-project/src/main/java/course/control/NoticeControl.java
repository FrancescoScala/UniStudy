package course.control;

import course.beans.Notice;
import course.manager.NoticeManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
                String mex = "";
                try {
                    NoticeManager.createNotice(request.getParameter("title"),
                            new Timestamp(System.currentTimeMillis()),
                            request.getParameter("description"),
                            Integer.parseInt(request.getParameter("id")));
                    mex="OK";
                } catch (SQLException | RuntimeException e) {
                    e.printStackTrace();
                    mex="AVVISO NON CREATO VERIFICA CHE IL FORMATO DEI DATI SIA CORRETTO";
                }
                finally {
                    JSONObject json = new JSONObject();
                    json.put("result", mex);
                    System.out.println("CreateNotice: action=add restituisce: " + mex);
                    out.print(json.toString());
                }
                break;

            case "view":
                try {
                    Set<Notice> notices = null;
                    notices = NoticeManager.retrieveNoticesByCourseId(Integer.parseInt(request.getParameter("id")));
                    System.out.println(notices);
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/course/gestorecorso/remove-alert.jsp?id="+request.getParameter("id"));
                    request.setAttribute("notices", notices);
                    dispatcher.forward(request,response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "delete":
                try {
                    NoticeManager.deleteNotice(Integer.parseInt(request.getParameter("noticeId")));
                    int id = Integer.parseInt(request.getParameter("id"));
                    Set<Notice> notices1 = NoticeManager.retrieveNoticesByCourseId(id);
                    RequestDispatcher dispatcher1 = this.getServletContext().getRequestDispatcher("/course/gestorecorso/remove-alert.jsp?id="+id);
                    request.setAttribute("notices", notices1);
                    dispatcher1.forward(request,response);
                } catch (SQLException | RuntimeException e) {
                    throw new RuntimeException(e);
                }
                break;

            default:
                // pagina 404
        }
    }
}
