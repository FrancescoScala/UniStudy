package it.unisa.dao;

import it.unisa.beans.Course;
import it.unisa.beans.Enrollment;
import it.unisa.beans.Note;
import it.unisa.beans.Role;
import it.unisa.db.ConnectionPoolDB;

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

    private static final String professorsRegex = "^[a-zA-Z\\s,]+$";

    private static final String scheduleRegex = "^((Lun|Mar|Mer|Gio|Ven)\\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9]\\s-\\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9],?\\s?)*$";
/*        public static boolean createCourse(String professors, String schedule, String title) {
        try {
            //controllo formato?
            if (professors.matches(professorsRegex) &&
                    password.matches(pswRegex) &&
                    name.matches(alphabeticRegex) &&
                    surname.matches(alphabeticRegex)) {
                //se l'email non è presente nel sistema...
                if (retrieveIdUserByEmail(email) == -1) {
                    String querySQL1 = "INSERT INTO user(user_email,user_password,user_name,user_surname) VALUES (?,?,?,?)";
                    PreparedStatement ps1 = conn.prepareStatement(querySQL1);

                    ps1.setString(1, email);
                    ps1.setString(2, password);
                    ps1.setString(3, name);
                    ps1.setString(4, surname);
                    ps1.executeUpdate();

                    return true;
                }
                //se già presente...
                else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

/*    public static Set<Enrollment> retrieveEnrollmentByUserId(int userId) {
        try {
            Set<Enrollment> enrollments = new HashSet<Enrollment>();
            String querySQL1 = "SELECT * FROM enrollment WHERE user_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(querySQL1);
            ps1.setInt(1, userId);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()) {

                Enrollment enrollment = new Enrollment();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }*/
/*    public static Course retrieveCourseById(int courseId) {
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
                    //Set<Note> notes = NoteManager.retrieveNoteByCourseId(courseId);
                    //Set<Notice> notices = NoteManager.retrieveNoticeByCourseId(courseId);
                   // course = new Course(courseId,courseProfessors,courseSchedule,courseTitle,note,notice);
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
    }*/
}
