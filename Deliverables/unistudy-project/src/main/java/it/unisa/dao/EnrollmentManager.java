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
    public static Set<Enrollment> retrieveEnrollmentByUserId(int userId) {
            try {
                Set<Enrollment> enrollments = new HashSet<Enrollment>();
                String querySQL1 = "SELECT * FROM enrollment WHERE user_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);
                ps1.setInt(1, userId);
                ResultSet rs1 = ps1.executeQuery();
                while(rs1.next()) {
                    int courseId = rs1.getInt("course_id");
                    String[] enrollTypeName = rs1.getString("enrollment_type").split(",");
                    Set<Enrollment.EnrollType> enrollTypes = new HashSet<>();
                    for(String s: enrollTypeName) {
                        Enrollment.EnrollType enrollType = Enrollment.createRoleType(s);
                        enrollTypes.add(enrollType);
                    }
                    Enrollment enrollment = new Enrollment(userId, courseId,enrollTypes);
                    enrollments.add(enrollment);
                }
                return enrollments;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
}
