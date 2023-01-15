package it.unisa.dao;

import it.unisa.beans.Enrollment;

import it.unisa.db.ConnectionPoolDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class EnrollmentManager {
    private static Connection conn; //final?

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String alphanumericRegex = "^[a-zA-Z0-9\\s]+$";

    public static Enrollment createEnrollment(int userId, int courseId, Enrollment.EnrollType enrollType,String courseTitle) {
        if(courseTitle.matches(alphanumericRegex)) {
            try {
                String querySQL1 = "INSERT INTO enrollment(user_id, course_id, enrollment_type) VALUES (?,?,?)";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);

                ps1.setInt(1, userId);
                ps1.setInt(2, courseId);
                ps1.setString(3, enrollType.toString());

                ps1.executeUpdate();
                ps1.close();
                //DA VEDERE SE POSSIBILE PASSARE IL TITOLO COME PARAMETRO ESPLICITO DELLA FUNZIONE
        /*  ResultSet resultSet = conn.prepareStatement("SELECT course_title FROM course WHERE course_id='" + courseId + "'").executeQuery();
            resultSet.next();
            String courseTitle = resultSet.getString("course_title");
        */
                Set<Enrollment.EnrollType> enrollTypes = new HashSet<>();
                enrollTypes.add(enrollType);

                return new Enrollment(userId, courseId, courseTitle, enrollTypes);

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            return null;
        }
    }

    public static Set<Enrollment> retrieveEnrollmentByUserId(int userId) {
        try {
            Set<Enrollment> enrollments = new HashSet<Enrollment>();
            String querySQL1 = "SELECT e.user_id,e.course_id,e.enrollment_type,c.course_title FROM enrollment AS e,course AS c WHERE e.user_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(querySQL1);
            ps1.setInt(1, userId);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                int courseId = rs1.getInt("e.course_id");
                String courseTitle = rs1.getString("c.course_tile");
                String[] enrollTypeName = rs1.getString("e.enrollment_type").split(",");
                Set<Enrollment.EnrollType> enrollTypes = new HashSet<>();
                for (String s : enrollTypeName) {
                    Enrollment.EnrollType enrollType = Enrollment.createRoleType(s);
                    enrollTypes.add(enrollType);
                }
                Enrollment enrollment = new Enrollment(userId, courseId, courseTitle, enrollTypes);
                enrollments.add(enrollment);
            }
            return enrollments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
