package course.manager;

import course.beans.Course;
import course.beans.Notice;
import connection.ConnectionPoolDB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class NoticeManager {
    private static Connection conn; //final?

    private static final String alphabeticTitleRegex = "^[a-zA-Z ]+${8,12}";


    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //notice needs to be unique. Can't be added if there's already a notice in the coruse with the same title...diagram?
    public static boolean createNotice(String title, Timestamp creationDate, String description, Course course) {
        //control in db by title
        if ((description.length() != 0) &&
                (description.length() <= 300) &&
                title.matches(alphabeticTitleRegex)) {
            try {
                String querySQL1 = "INSERT INTO notice(notice_description,notice_creation_date,notice_title,course_id) VALUES (?,?,?,?)";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);

                ps1.setString(1, description);
                ps1.setTimestamp(2, creationDate);
                ps1.setString(3, title);
                ps1.setInt(4, course.getId());
                ps1.executeUpdate();
                ps1.close();

                ResultSet resultSet = conn.prepareStatement("SELECT notice_id FROM notice WHERE notice_creation_date='" + creationDate + "'").executeQuery();
                resultSet.next();
                int noticeId = resultSet.getInt("notice_id");
                course.addNotice(new Notice(noticeId, title, creationDate, description));
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else
            return false;
    }

    public static Set<Notice> retrieveNoticesByCourseId(int courseId) {
        try {
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

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteNotice(int id) { // da testare
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM notice WHERE notice_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
