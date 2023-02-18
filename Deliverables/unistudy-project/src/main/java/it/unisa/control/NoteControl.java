package it.unisa.control;

import it.unisa.beans.Course;
import it.unisa.beans.Note;
import it.unisa.beans.User;
import it.unisa.dao.CourseManager;
import it.unisa.dao.NoteManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
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
                Course course = CourseManager.retrieveCourseById(Integer.parseInt(request.getParameter("id")));
                User author = (User) request.getSession().getAttribute("userInSession");
                System.out.println("Dati arrivati a addNote: " +
                        request.getParameter("description") +
                        request.getParameter("title") +
                        author.getId() +
                        author.getName() + author.getSurname() +
                        course);

                boolean check = NoteManager.createNote(request.getParameter("description"),
                        new Timestamp(System.currentTimeMillis()),
                        "/ciao/filepath.img",
                        request.getParameter("title"),
                        author.getId(),
                        author.getName() + " " + author.getSurname(),
                        course);
                String mex;
                if (check) {
                    mex = "OK";
                } else {
                    mex = "Aggiunta nota fallita. Reinsirisci i dati nel formato errato corretto e riprova";
                }
                JSONObject json = new JSONObject();
                json.put("result", mex);
                System.out.println("addNote restituisce: " + mex);
                out.print(json.toString());
                break;

            case "delete":
                String askerRole = request.getParameter("role");
                if (askerRole == null) {
                    User user = (User) request.getSession().getAttribute("userInSession");
                    int noteAuthorId = Integer.parseInt(request.getParameter("noteAuthorId"));
                    if (user.getId() == noteAuthorId) {
                        boolean checkDelete = NoteManager.deleteNote(Integer.parseInt(request.getParameter("noteId")));
                        if (checkDelete) {
                            int id = Integer.parseInt(request.getParameter("id"));
                            Course course1 = CourseManager.retrieveCourseById(id);
                            request.setAttribute("course", course1);
                            request.getRequestDispatcher("/partecipante/corso/view_course.jsp?id=" + course1.getId() + "").forward(request, response);
                        }
                        // else pagina di errore
                    }
                } else {
                    boolean checkDelete1 = NoteManager.deleteNote(Integer.parseInt(request.getParameter("noteId")));
                    if (checkDelete1) {
                        int id = Integer.parseInt(request.getParameter("id"));
                        Set<Note> notes = NoteManager.retrieveNotesByCourseId(id);
                        System.out.println(notes);
                        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/partecipante/corso/gestore/remove-note.jsp?id=" + id);
                        request.setAttribute("notes", notes);
                        dispatcher.forward(request, response);
                    }
                }
                break;

            case "view":
                System.out.println("Sono in note view");
                Set<Note> notes = NoteManager.retrieveNotesByCourseId(Integer.parseInt(request.getParameter("id")));
                System.out.println("Note:" + notes);
                RequestDispatcher dispatcher = this.getServletContext()
                        .getRequestDispatcher("/partecipante/corso/gestore/remove-note.jsp?id=" + request.getParameter("id"));
                request.setAttribute("notes", notes);
                dispatcher.forward(request, response);
                break;
            default:
        }
    }
}
