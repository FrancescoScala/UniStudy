package course.manager;

import course.beans.Note;
import connection.ConnectionPoolDB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class NoteManager {
    private static Connection conn;
    private static final String titleRegex = "^.{1,50}$";
    private static final String authorRegex = "^[a-zA-Z ]+$";
    private static final String pathRegex = "^(\\/[A-Za-z0-9_-]+)+\\.(txt|pdf|png|docx|doc|jpeg|jpg|img)$";
    private static final String descriptionRegex = "^.{1,300}$";

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createNote(String description, Timestamp creationDate, String filepath, String title, int authorId, String authorInfo, int courseId) throws NullPointerException, SQLException {
        // check that the regexes match
        if (authorInfo.matches(authorRegex) &&
                description.matches(descriptionRegex) &&
                title.matches(titleRegex) &&
                filepath.matches(pathRegex)) {

            String querySQL1 = "INSERT INTO note(note_title, note_description, note_creation_date, note_path, course_id, user_id) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps1 = conn.prepareStatement(querySQL1);

            ps1.setString(1, title);
            ps1.setString(2, description);
            ps1.setTimestamp(3, creationDate);
            ps1.setString(4, filepath);
            ps1.setInt(5, courseId);
            ps1.setInt(6, authorId);
            ps1.executeUpdate();
            ps1.close();
            return true;
        }
        // regexes are not respected
        else
            throw new RuntimeException("Formato errato");
    }
    // retrieve course's notes
    public static Set<Note> retrieveNotesByCourseId(int courseId) throws SQLException {
        Set<Note> notes = new HashSet<Note>();
        String querySQL = "SELECT note_id, note_title, note_description, note_creation_date, note_path, " +
                "note.user_id, user.user_id, user_name, user_surname " +
                "FROM note, user " +
                "WHERE course_id=? AND note.user_id=user.user_id";
        PreparedStatement ps = conn.prepareStatement(querySQL);
        ps.setInt(1, courseId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            do {
                int id = rs.getInt("note_id");
                String description = rs.getString("note_description");
                Timestamp creationDate = rs.getTimestamp("note_creation_date");
                String filepath = rs.getString("note_path");
                String title = rs.getString("note_title");
                int authorId = rs.getInt("user_id");
                String authorInfo = rs.getString("user_name") + " " + rs.getString("user_surname");
                Note note = new Note(id, description, creationDate, filepath, title, authorId, authorInfo);
                notes.add(note);
            } while (rs.next());
        }
        return notes;
    }

    public static boolean deleteNote(int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM note WHERE note_id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        return true;
    }
}
