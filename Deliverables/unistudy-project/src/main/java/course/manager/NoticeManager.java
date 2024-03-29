package course.manager;

import course.beans.Notice;
import connection.ConnectionPoolDB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class NoticeManager {
    private static Connection conn;

    private static final String titleRegex = "^.{1,50}$";
    private static final String descriptionRegex = "^.{1,300}$";

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean createNotice(String title, Timestamp creationDate, String description, int courseId) throws SQLException {
        // check that the regexes match
        if (description.matches(descriptionRegex) &&
                title.matches(titleRegex)) {
            String querySQL1 = "INSERT INTO notice(notice_description,notice_creation_date,notice_title,course_id) VALUES (?,?,?,?)";
            PreparedStatement ps1 = conn.prepareStatement(querySQL1);
            ps1.setString(1, description);
            ps1.setTimestamp(2, creationDate);
            ps1.setString(3, title);
            ps1.setInt(4, courseId);
            ps1.executeUpdate();
            ps1.close();
            return true;
        }
        // regexes are not respected
        else
            throw new RuntimeException("Formato errato");
    }
    // retrieve course's notices
    public static Set<Notice> retrieveNoticesByCourseId(int courseId) throws SQLException {
        Set<Notice> notices = new HashSet<Notice>();
        String querySQL = "SELECT * FROM notice WHERE course_id=?";
        PreparedStatement ps = conn.prepareStatement(querySQL);
        ps.setInt(1, courseId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            do {
                int id = rs.getInt("notice_id");
                String title = rs.getString("notice_title");
                Timestamp creationDate = rs.getTimestamp("notice_creation_date");
                String description = rs.getString("notice_description");

                Notice notice = new Notice(id, title, creationDate, description);
                notices.add(notice);
            } while (rs.next());

        }
        return notices;
    }

    public static boolean deleteNotice(int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM notice WHERE notice_id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        return true;
    }
}
