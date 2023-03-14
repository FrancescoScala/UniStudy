import course.beans.Course;
import course.beans.Enrollment;
import course.beans.Note;
import course.beans.Notice;
import course.manager.CourseManager;
import course.manager.EnrollmentManager;
import connection.ConnectionPoolDB;
import org.junit.jupiter.api.*;
import user.beans.Member;
import user.beans.Role;
import user.manager.MemberManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;

import static course.beans.Enrollment.EnrollType.GESTORECORSO;
import static course.beans.Enrollment.EnrollType.STUDENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnrollmentManagerTest {
    private Member memberForTesting;
    private Enrollment enrollmentForTesting;
    private Set<Enrollment> enrollmentsForTesting;
    private Course courseForTesting;
    @BeforeAll
    void setUp() throws SQLException {
        //create the course for testing
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";
        Set<Note> notes = new HashSet<Note>();
        Set<Notice> notices = new HashSet<Notice>();
        courseForTesting = new Course(-2, professors,schedule,title,notices,notes);
        CourseManager.createCourse(courseForTesting.getProfessors(),courseForTesting.getTimeSchedule(),courseForTesting.getTitle());
        courseForTesting.setId(CourseManager.retrieveIdCourseByTitle(courseForTesting.getTitle()));

        //create the author for testing
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";
        Set<Role> memberRoles = new HashSet<Role>();
        memberForTesting = new Member(-1, email, password, name, surname, memberRoles);

        //save the author in the db. Take the real id
        MemberManager.signupMember(email, password, name, surname);
        memberForTesting.setId(MemberManager.retrieveIdMemberByEmail(email));
        //create the enrollment for testing, using the id of the author just created
        Set<Enrollment.EnrollType> enrollRoles = new HashSet<Enrollment.EnrollType>();
        enrollRoles.add(STUDENTE);
        enrollmentForTesting = new Enrollment(memberForTesting.getId(),courseForTesting.getId(),courseForTesting.getTitle(),enrollRoles);
        enrollmentsForTesting = new HashSet<Enrollment>();
        enrollmentsForTesting.add(enrollmentForTesting);
    }

    @AfterAll
    void tearDown() throws SQLException {
        enrollmentsForTesting=null;
        Connection con = ConnectionPoolDB.getConnection();

        PreparedStatement ps = con.prepareStatement("DELETE FROM enrollment WHERE user_id=? AND course_id=?");
        ps.setInt(1, enrollmentForTesting.getMemberId());
        ps.setInt(2, enrollmentForTesting.getCourseId());
        ps.executeUpdate();
        ps.close();

        PreparedStatement ps1 = con.prepareStatement("DELETE FROM user WHERE user_id=?");
        ps1.setInt(1, memberForTesting.getId());
        ps1.executeUpdate();
        ps1.close();

        PreparedStatement ps2 = con.prepareStatement("DELETE FROM course WHERE course_id=?");
        ps2.setInt(1, courseForTesting.getId());
        ps2.executeUpdate();
        ps2.close();

        con.close();
    }

    @Order(1)
    @Test
    void createEnrollmentSuccess()
    {
        Enrollment enrollment = EnrollmentManager.createEnrollment(enrollmentForTesting.getMemberId(), enrollmentForTesting.getCourseId(), STUDENTE,enrollmentForTesting.getCourseTitle());

        assertEquals(enrollmentForTesting, enrollment);
    }

    @Test
    void createEnrollmentMemberIdNotValid()
    {
        Enrollment enrollment = EnrollmentManager.createEnrollment(-8, enrollmentForTesting.getCourseId(), STUDENTE,enrollmentForTesting.getCourseTitle());

        assertNull(enrollment);
    }

    @Test
   void createEnrollmentCourseIdNotValid()
    {
        Enrollment enrollment = EnrollmentManager.createEnrollment(enrollmentForTesting.getMemberId(), -8, STUDENTE,enrollmentForTesting.getCourseTitle());

        assertNull(enrollment);
    }

    @Test
    void createEnrollmentCourseTitleBadFormatted()
    {
        Enrollment enrollment = EnrollmentManager.createEnrollment(enrollmentForTesting.getMemberId(), enrollmentForTesting.getCourseId(), STUDENTE,"");

        assertNull(enrollment);
    }

    @Test
    void unenrollSTUDENTE() throws SQLException {
        Set<Enrollment.EnrollType> mockedRoles = mock(Set.class);
        when(mockedRoles.size()).thenReturn(2);

        Enrollment e = mock(Enrollment.class);
        when(e.getRoles()).thenReturn(mockedRoles);
        when(e.getCourseId()).thenReturn(enrollmentForTesting.getCourseId());
        when(e.getMemberId()).thenReturn(enrollmentForTesting.getMemberId());

        Boolean check = EnrollmentManager.unenroll(STUDENTE,e);
        assertTrue(check);
    }

    @Test
    void unenrollGESTORECORSO() throws SQLException {
        Set<Enrollment.EnrollType> mockedRoles = mock(Set.class);
        when(mockedRoles.size()).thenReturn(2);

        Enrollment e = mock(Enrollment.class);
        when(e.getRoles()).thenReturn(mockedRoles);
        when(e.getCourseId()).thenReturn(enrollmentForTesting.getCourseId());
        when(e.getMemberId()).thenReturn(enrollmentForTesting.getMemberId());

        Boolean check = EnrollmentManager.unenroll(GESTORECORSO,e);
        assertTrue(check);
    }

    @Test
    void unenrollDeletion() throws SQLException {
        Set<Enrollment.EnrollType> mockedRoles = mock(Set.class);
        when(mockedRoles.size()).thenReturn(1);

        Enrollment e = mock(Enrollment.class);
        when(e.getRoles()).thenReturn(mockedRoles);
        when(e.getCourseId()).thenReturn(enrollmentForTesting.getCourseId());
        when(e.getMemberId()).thenReturn(enrollmentForTesting.getMemberId());

        Boolean check = EnrollmentManager.unenroll(STUDENTE,e);
        assertTrue(check);
    }

    @Test
    void retrieveEnrollmentsByMemberIdSuccess() throws SQLException {
        int id = memberForTesting.getId();
        Set<Enrollment> enrollments = EnrollmentManager.retrieveEnrollmentsByMemberId(id);
        assertNotNull(enrollments);
    }

    @Test
    void updateEnrollmentSuccess() throws SQLException {
        assertNotNull(EnrollmentManager.updateEnrollment(enrollmentForTesting, GESTORECORSO));
    }

/*    @Test
    void retrieveEnrollmentsByMemberIdNotValid() {
        int id = -3;
        Set<Enrollment> enrollments = EnrollmentManager.retrieveEnrollmentsByMemberId(id);
        assertNull(enrollments);
    }*/

}
