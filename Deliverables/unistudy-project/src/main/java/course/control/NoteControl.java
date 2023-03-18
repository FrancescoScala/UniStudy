package course.control;

import course.beans.Course;
import course.beans.Note;
import user.beans.Member;
import course.manager.CourseManager;
import course.manager.NoteManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Set;

@WebServlet(name = "NoteControl", value = "/NoteControl")
public class NoteControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getParameter("action")) {
            case "add":
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();

                Member author = (Member) request.getSession().getAttribute("memberInSession");
                System.out.println("Dati arrivati a addNote: " +
                        request.getParameter("description") +
                        request.getParameter("title") +
                        author.getId() +
                        author.getName() + author.getSurname() +
                        Integer.parseInt(request.getParameter("id")));
                String mex = "";
                try {
                    NoteManager.createNote(request.getParameter("description"),
                            new Timestamp(System.currentTimeMillis()),
                            "/ciao/filepath.img",
                            request.getParameter("title"),
                            author.getId(),
                            author.getName() + " " + author.getSurname(),
                            Integer.parseInt(request.getParameter("id")));
                    mex = "OK";
                } catch (SQLException | RuntimeException e) {
                    e.printStackTrace();
                    mex = "Aggiunta nota fallita. Reinserisci i dati nel formato errato corretto e riprova";
                } finally {
                    JSONObject json = new JSONObject();
                    json.put("result", mex);
                    System.out.println("addNote restituisce: " + mex);
                    out.print(json.toString());
                }
                break;

            case "delete":
                String askerRole = request.getParameter("role");
                if (askerRole == null) {
                    Member member = (Member) request.getSession().getAttribute("memberInSession");
                    int noteAuthorId = Integer.parseInt(request.getParameter("noteAuthorId"));
                    if (member.getId() == noteAuthorId) {
                        try {
                            NoteManager.deleteNote(Integer.parseInt(request.getParameter("noteId")));
                            int id = Integer.parseInt(request.getParameter("id"));
                            Course course1 = CourseManager.retrieveCourseById(id);
                            request.setAttribute("course", course1);
                            request.getRequestDispatcher("/course/studente/view_course.jsp?id=" + course1.getId() + "").forward(request, response);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    try {
                        NoteManager.deleteNote(Integer.parseInt(request.getParameter("noteId")));
                        int id = Integer.parseInt(request.getParameter("id"));
                        Set<Note> notes = null;
                        notes = NoteManager.retrieveNotesByCourseId(id);
                        System.out.println(notes);
                        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/course/gestorecorso/remove-note.jsp?id=" + id);
                        request.setAttribute("notes", notes);
                        dispatcher.forward(request, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;

            case "view":
                try {
                    System.out.println("Sono in note view");
                    Set<Note> notes = null;
                    notes = NoteManager.retrieveNotesByCourseId(Integer.parseInt(request.getParameter("id")));
                    System.out.println("Note:" + notes);
                    RequestDispatcher dispatcher = this.getServletContext()
                            .getRequestDispatcher("/course/gestorecorso/remove-note.jsp?id=" + request.getParameter("id"));
                    request.setAttribute("notes", notes);
                    dispatcher.forward(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
        }
    }
}
