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
    private static Connection conn;

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String professorsRegex = "^(?:[a-zA-Z](?:\\s[a-zA-Z]+)?(?:,\\s?(?=[a-zA-Z]))?)+(?!,$)$";
    private static final String scheduleRegex = "^((Lun|Mar|Mer|Gio|Ven)\\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9]\\s-\\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9],?\\s?)*$";
    private static final String alphanumericRegex = "^.{1,50}$";

    public static boolean createCourse(String professors, String schedule, String title) throws SQLException {
        if (professors.matches(professorsRegex) &&
                schedule.matches(scheduleRegex) &&
                title.matches(alphanumericRegex)) {
            // if there isn't already a course with the same name
            if (retrieveIdCourseByTitle(title) == -1) {
                String querySQL1 = "INSERT INTO course(course_professors,course_schedule,course_title) VALUES (?,?,?)";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);
                ps1.setString(1, professors);
                ps1.setString(2, schedule);
                ps1.setString(3, title);
                ps1.executeUpdate();
                return true;
            } else {
                throw new RuntimeException("Formato errato");
            }
        }
        // if input data format is not valid
        else {
            throw new RuntimeException("Id errato");
        }
    }

    // modify course's info
    public static boolean modifyInfoCourse(Course course, String newProfessors, String newSchedule) throws SQLException {
        // if input data format is valid
        if (newProfessors.matches(professorsRegex) && newSchedule.matches(scheduleRegex) && retrieveIdCourseByTitle(course.getTitle()) != -1) {
            String querySQL = "UPDATE course SET course_professors = ? , course_schedule = ? WHERE course_id=?";
            PreparedStatement ps = conn.prepareStatement(querySQL);
            ps.setString(1, newProfessors);
            ps.setString(2, newSchedule);
            ps.setInt(3, course.getId());
            ps.executeUpdate();
            return true;
        } else {
            throw new RuntimeException("Formato errato");
        }
    }

    // retrieve Course by passing courseId as an explicit parameter
    public static Course retrieveCourseById(int courseId) {
        try {
            Course course;
            String querySQL1 = "SELECT * FROM course WHERE course_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(querySQL1);
            ps1.setInt(1, courseId);
            ResultSet rs1 = ps1.executeQuery();
            // if course's id is valid
            if (rs1.next()) {
                String courseProfessors = rs1.getString("course_professors");
                String courseSchedule = rs1.getString("course_schedule");
                String courseTitle = rs1.getString("course_title");
                Set<Note> notes = NoteManager.retrieveNotesByCourseId(courseId);
                Set<Notice> notices = NoticeManager.retrieveNoticesByCourseId(courseId);
                course = new Course(courseId, courseProfessors, courseSchedule, courseTitle, notices, notes);
                return course;
            }
            else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // retrieve Course by its title
    public static int retrieveIdCourseByTitle(String courseTitle) {
        try {
            String querySQL = "SELECT course.course_id FROM course WHERE course_title=?";
            PreparedStatement ps = conn.prepareStatement(querySQL);
            ps.setString(1, courseTitle);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("course_id");
            // if there's no course for the given title
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // retrieve all the Course instances in the db
    public static Set<Course> retrieveAll() {
        try {
            String querySQL = "SELECT * FROM course";
            PreparedStatement ps = conn.prepareStatement(querySQL);
            ResultSet rs = ps.executeQuery();
            Set<Course> courses = new HashSet<Course>();
            if (rs.next()) {
                do {
                    int id = rs.getInt("course_id");
                    String professors = rs.getString("course_professors");
                    String schedule = rs.getString("course_schedule");
                    String title = rs.getString("course_title");
                    Course course = new Course(id, professors, schedule, title, null, null);
                    courses.add(course);
                } while (rs.next());
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // delete a Course
    public static boolean deleteCourse(int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM course WHERE course_id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        return true;
    }
}
