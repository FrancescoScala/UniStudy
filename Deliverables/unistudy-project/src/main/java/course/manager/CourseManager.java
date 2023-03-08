package course.manager;

import course.beans.Course;
import course.beans.Note;
import course.beans.Notice;
import connection.ConnectionPoolDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CourseManager {
    private static Connection conn; //final?

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String professorsRegex = "^(?:[a-zA-Z](?:\\s[a-zA-Z]+)?(?:,\\s?(?=[a-zA-Z]))?)+(?!,$)$";//^[a-zA-Z\s,]+$

    private static final String scheduleRegex = "^((Lun|Mar|Mer|Gio|Ven)\\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9]\\s-\\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9],?\\s?)*$";

    private static final String alphanumericRegex = "^.{1,50}$";//""^[a-zA-Z0-9\\s]+$";

    public static boolean createCourse(String professors, String schedule, String title) {
        try {
            //controllo formato?
            if (professors.matches(professorsRegex) &&
                    schedule.matches(scheduleRegex) &&
                    title.matches(alphanumericRegex)) {
                //se che NON esiste un corso avente lo stesso nome
                if (retrieveIdCourseByTitle(title) == -1) {
                    String querySQL1 = "INSERT INTO course(course_professors,course_schedule,course_title) VALUES (?,?,?)";
                    PreparedStatement ps1 = conn.prepareStatement(querySQL1);

                    ps1.setString(1, professors);
                    ps1.setString(2, schedule);
                    ps1.setString(3, title);
                    ps1.executeUpdate();

                    return true;
                } else {//se esiste un corso con lo stesso nome
                    return false;
                }
            } else {//se il formato non è valido
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean modifyInfoCourse(Course course, String newProfessor, String newSchedule) {
        if(newProfessor.matches(professorsRegex) && newSchedule.matches(scheduleRegex)) {
            try {
                String querySQL = "UPDATE course SET course_professors = ? , course_schedule = ? WHERE course_id=?";
                PreparedStatement ps = conn.prepareStatement(querySQL);
                ps.setString(1, newProfessor);
                ps.setString(2, newSchedule);
                ps.setInt(3, course.getId());
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    public static Course retrieveCourseById(int courseId) {
            try {
                Course course;
                String querySQL1 = "SELECT * FROM course WHERE course_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);
                ps1.setInt(1, courseId);
                ResultSet rs1 = ps1.executeQuery();
                //se id è presente
                if(rs1.next()) {
                    String courseProfessors = rs1.getString("course_professors");
                    String courseSchedule = rs1.getString("course_schedule");
                    String courseTitle = rs1.getString("course_title");
                    Set<Note> notes = NoteManager.retrieveNotesByCourseId(courseId);
                    Set<Notice> notices = NoticeManager.retrieveNoticesByCourseId(courseId);
                    course = new Course(courseId,courseProfessors,courseSchedule,courseTitle, notices, notes);
                    return course;
                }
                //se id non è presente
                else {
                    return null;
                }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int retrieveIdCourseByTitle(String courseTitle) {
        try {
            String querySQL = "SELECT course.course_id FROM course WHERE course_title=?";
            PreparedStatement ps = conn.prepareStatement(querySQL);

            ps.setString(1, courseTitle);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("course_id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Set<Course> retrieveAll() {
        try {
            String querySQL = "SELECT * FROM course";
            PreparedStatement ps = conn.prepareStatement(querySQL);

            ResultSet rs = ps.executeQuery();
            Set<Course> courses = new HashSet<Course>();
            if(rs.next()) {
                do {
                    int id = rs.getInt("course_id");
                    String professors = rs.getString("course_professors");
                    String schedule = rs.getString("course_schedule");
                    String title = rs.getString("course_title");
                    Course course = new Course(id,professors,schedule,title,null,null);
                    courses.add(course);
                } while (rs.next());
            }
            else
                return null;
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteCourse(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM course WHERE course_id=?");
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
