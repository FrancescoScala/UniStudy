package it.unisa.dao;

import it.unisa.beans.*;
import it.unisa.db.ConnectionPoolDB;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;

import static it.unisa.beans.Enrollment.EnrollType.STUDENTE;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnrollmentManagerTest {
    private User userForTesting;

    private Enrollment enrollmentForTesting;
    private Set<Enrollment> enrollmentsForTesting;
    private Course courseForTesting;
    @BeforeAll
    void setUp() {
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
        Set<Role> userRoles = new HashSet<Role>();
        userRoles.add(new Role(1, "PARTECIPANTE"));
        userForTesting = new User(-1, email, password, name, surname, userRoles);

        //save the author in the db. Take the real id
        UserManager.signupUser(email, password, name, surname);
        userForTesting.setId(UserManager.retrieveIdUserByEmail(email));
        //create the enrollment for testing, using the id of the author just created
        Set<Enrollment.EnrollType> enrollRoles = new HashSet<Enrollment.EnrollType>();
        enrollRoles.add(STUDENTE);
        enrollmentForTesting = new Enrollment(userForTesting.getId(),courseForTesting.getId(),courseForTesting.getTitle(),enrollRoles);
        enrollmentsForTesting = new HashSet<Enrollment>();
        enrollmentsForTesting.add(enrollmentForTesting);
    }

    @AfterAll
    void tearDown() throws SQLException {
        enrollmentsForTesting=null;
        Connection con = ConnectionPoolDB.getConnection();

        PreparedStatement ps = con.prepareStatement("DELETE FROM enrollment WHERE user_id=? AND course_id=?");
        ps.setInt(1, enrollmentForTesting.getUserId());
        ps.setInt(2, enrollmentForTesting.getCourseId());
        ps.executeUpdate();
        ps.close();

        PreparedStatement ps1 = con.prepareStatement("DELETE FROM user WHERE user_id=?");
        ps1.setInt(1, userForTesting.getId());
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
        Enrollment enrollment = EnrollmentManager.createEnrollment(enrollmentForTesting.getUserId(), enrollmentForTesting.getCourseId(), STUDENTE,enrollmentForTesting.getCourseTitle());

        assertEquals(enrollmentForTesting, enrollment);
    }

    @Test
    void createEnrollmentUserIdNotValid()
    {
        Enrollment enrollment = EnrollmentManager.createEnrollment(-8, enrollmentForTesting.getCourseId(), STUDENTE,enrollmentForTesting.getCourseTitle());

        assertNull(enrollment);
    }

    @Test
    void createEnrollmentCourseIdNotValid()
    {
        Enrollment enrollment = EnrollmentManager.createEnrollment(enrollmentForTesting.getUserId(), -8, STUDENTE,enrollmentForTesting.getCourseTitle());

        assertNull(enrollment);
    }

    @Test
    void createEnrollmentCourseTitleEmpty()
    {
        Enrollment enrollment = EnrollmentManager.createEnrollment(enrollmentForTesting.getUserId(), enrollmentForTesting.getCourseId(), STUDENTE,"");

        assertNull(enrollment);
    }

    @Test
    void createEnrollmentCourseTitleBadFormatted()
    {
        Enrollment enrollment = EnrollmentManager.createEnrollment(enrollmentForTesting.getUserId(), enrollmentForTesting.getCourseId(), STUDENTE,"Ingegneria del software!!!");

        assertNull(enrollment);
    }

    @Test
    void retrieveEnrollmentsByUserIdSuccess() {
        int id = userForTesting.getId();
        Set<Enrollment> enrollments = EnrollmentManager.retrieveEnrollmentsByUserId(id);
        assertEquals(enrollmentsForTesting,enrollments);
    }

    @Test
    void retrieveEnrollmentsByUserIdNotValid() {
        int id = -3;
        Set<Enrollment> enrollments = EnrollmentManager.retrieveEnrollmentsByUserId(id);
        assertNull(enrollments);
    }

}
