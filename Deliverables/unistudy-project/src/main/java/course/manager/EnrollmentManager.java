package course.manager;

import course.beans.Enrollment;
import connection.ConnectionPoolDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class EnrollmentManager {
    private static Connection conn;

    static {
        try {
            conn = ConnectionPoolDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String alphanumericRegex = "^.{1,50}$";//^[a-zA-Z0-9\s]+$

    // create a new Enrollment
    public static Enrollment createEnrollment(int memberId, int courseId, Enrollment.EnrollType enrollType, String courseTitle) {
        if (courseTitle.matches(alphanumericRegex)) {
            try {
                String querySQL1 = "INSERT INTO enrollment(user_id, course_id, enrollment_type) VALUES (?,?,?)";
                PreparedStatement ps1 = conn.prepareStatement(querySQL1);
                ps1.setInt(1, memberId);
                ps1.setInt(2, courseId);
                ps1.setString(3, enrollType.toString());
                ps1.executeUpdate();
                ps1.close();

                Set<Enrollment.EnrollType> enrollTypes = new HashSet<>();
                enrollTypes.add(enrollType);
                return new Enrollment(memberId, courseId, courseTitle, enrollTypes);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    // retrieve Enrollment by passing Member id
    public static Set<Enrollment> retrieveEnrollmentsByMemberId(int memberId) throws SQLException { // test case not valid...
        Set<Enrollment> enrollments = new HashSet<Enrollment>();
        String querySQL1 = "SELECT e.user_id,e.course_id,e.enrollment_type,c.course_title FROM enrollment AS e,course AS c WHERE e.user_id = ? AND e.course_id=c.course_id";
        PreparedStatement ps1 = conn.prepareStatement(querySQL1);
        ps1.setInt(1, memberId);
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) {
            do {
                int courseId = rs1.getInt("e.course_id");
                String courseTitle = rs1.getString("c.course_title");

                // necessary to identify different roles in the same entry, as the format of e.enrollment_type is like "STUDENTE, GESTORECORSO"
                String[] enrollTypeName = rs1.getString("e.enrollment_type").split(",");
                Set<Enrollment.EnrollType> enrollTypes = new HashSet<>();
                for (String s : enrollTypeName) {
                    Enrollment.EnrollType enrollType = Enrollment.createRoleType(s);
                    enrollTypes.add(enrollType);
                }
                Enrollment enrollment = new Enrollment(memberId, courseId, courseTitle, enrollTypes);
                enrollments.add(enrollment);
            }
            while (rs1.next());
        }
        return enrollments;
    }

    public static boolean unenroll(Enrollment.EnrollType enrollType, Enrollment e) throws SQLException {
        // if the Member has more than 1 role, so it's both STUDENTE and GESTORECORSO
        if (e.getRoles().size() > 1)
            return removeRoleEnrollment(e.getMemberId(), e.getCourseId(), enrollType);
        // if he has only 1 role, so it's just STUDENTE
        else
            return deleteEnrollment(e.getMemberId(), e.getCourseId());
    }

    // remove the required enrollType from the given Enrollment
    private static boolean removeRoleEnrollment(int memberId, int courseId, Enrollment.EnrollType enrollType) throws SQLException // da testare con xategory su enrolltype
    {
        PreparedStatement ps;
        if (enrollType.toString().equals("STUDENTE"))
            ps = conn.prepareStatement("UPDATE unistudydb.enrollment t SET t.enrollment_type = 'GESTORECORSO' WHERE t.course_id =? AND t.user_id =?");
        else
            ps = conn.prepareStatement("UPDATE unistudydb.enrollment t SET t.enrollment_type = 'STUDENTE' WHERE t.course_id =? AND t.user_id =?");
        ps.setInt(1, courseId);
        ps.setInt(2, memberId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    // delete the given Enrollment, identified by the couple memberId and courseId
    private static boolean deleteEnrollment(int memberId, int courseId) throws SQLException // da testare
    {
        PreparedStatement ps;
        ps = conn.prepareStatement("DELETE FROM enrollment WHERE course_id=? AND user_id=?");
        ps.setInt(1, courseId);
        ps.setInt(2, memberId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    // update an Enrollment by adding the given enrollType to it
    public static Enrollment updateEnrollment(Enrollment enrollment, Enrollment.EnrollType type) throws SQLException { //da testare
        enrollment.getRoles().add(type);
        String up = "";
        for (Enrollment.EnrollType enrollType : enrollment.getRoles())
            up += enrollType.toString() + ",";
        System.out.println(up);
        up = up.substring(0, up.length() - 1);
        System.out.println(up);
        PreparedStatement ps = conn.prepareStatement("UPDATE unistudydb.enrollment t SET t.enrollment_type = '" + up + "' WHERE t.course_id =? AND t.user_id =?");

        ps.setInt(1, enrollment.getCourseId());
        ps.setInt(2, enrollment.getMemberId());
        ps.executeUpdate();
        ps.close();
        return enrollment;
    }
}
