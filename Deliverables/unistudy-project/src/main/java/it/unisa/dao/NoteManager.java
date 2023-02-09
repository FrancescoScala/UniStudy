package it.unisa.dao;

import it.unisa.beans.Course;
import it.unisa.beans.Note;
import it.unisa.db.ConnectionPoolDB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class NoteManager {
    private static Connection conn; //final?
    private static final String alphabeticRegex = "^[a-zA-Z ]+$";
    private static final String pathRegex = "^(\\/[A-Za-z0-9_-]+)+\\.(txt|pdf|png|docx|doc|jpeg|jpg|img)$";
    private static final String alphanumericRegex = "^[a-zA-Z0-9\\s]+$";

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //note needs to be unique. Can't be added if there's already a note in the course with the same title...diagram?
    //controllo formato sul path? O sulla dimensione della description e del titolo?
    public static boolean createNote(String description, Timestamp creationDate, String filepath, String title, int authorId, String authorInfo, Course course) {
        try {
            if (authorInfo.matches(alphabeticRegex) &&
                    (description.length()!=0) &&
                    (description.length() <= 300) &&
                    title.matches(alphanumericRegex) &&
                    title.length()!=0 &&
                    filepath.matches(pathRegex)) {

                String querySQL1 = "INSERT INTO note(note_title, note_description, note_creation_date, note_path, course_id, user_id) VALUES (?,?,?,?,?,?)";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);

                ps1.setString(1, title);
                ps1.setString(2, description);
                ps1.setTimestamp(3, creationDate);
                ps1.setString(4, filepath);
                ps1.setInt(5, course.getId());
                ps1.setInt(6, authorId);
                ps1.executeUpdate();
                ps1.close();
                ResultSet resultSet = conn.prepareStatement("SELECT note_id FROM note WHERE user_id='" + authorId + "' AND note_creation_date='" + creationDate + "'").executeQuery();
                resultSet.next();
                int noteId = resultSet.getInt("note_id");
                course.addNote(new Note(noteId, description, creationDate, filepath, title, authorId, authorInfo));
                return true;
            } else
                return false;
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Set<Note> retrieveNotesByCourseId(int courseId) {
        try {
            Set<Note> notes = new HashSet<Note>();
            String querySQL = "SELECT note_id, note_title, note_description, note_creation_date, note_path, " +
                    "note.user_id, user.user_id, user_name, user_surname " +
                    "FROM note, user " +
                    "WHERE course_id=? AND note.user_id=user.user_id";
            PreparedStatement ps = conn.prepareStatement(querySQL);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                do {
                    int id = rs.getInt("note_id");
                    String description = rs.getString("note_description");
                    Timestamp creationDate = rs.getTimestamp("note_creation_date");
                    String filepath = rs.getString("note_path");
                    String title = rs.getString("note_title");
                    int authorId = rs.getInt("user_id");
                    String authorinfo = rs.getString("user_name") + " " + rs.getString("user_surname");
                    Note note = new Note(id, description, creationDate, filepath, title, authorId, authorinfo);
                    notes.add(note);
                } while (rs.next());
            }
            return notes;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
