package it.unisa.dao;

import it.unisa.beans.*;
import it.unisa.db.ConnectionPoolDB;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseManagerTest {
    private Course courseForTesting;

    @BeforeAll
    void setUp() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";
        Set<Note> notes = new HashSet<Note>();
        Set<Notice> notices = new HashSet<Notice>();

        courseForTesting = new Course(-2, professors,schedule,title,notices,notes);
    }

    @AfterAll
    void tearDown() {
        try {
            Connection con = ConnectionPoolDB.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM course WHERE course_id=?");
            ps.setInt(1,courseForTesting.getId());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Order(1)
    @Test
    void createCourseSuccess() {

        boolean check = CourseManager.createCourse(courseForTesting.getProfessors(),courseForTesting.getTimeSchedule(),courseForTesting.getTitle());
        courseForTesting.setId(CourseManager.retrieveIdCourseByTitle(courseForTesting.getTitle()));
        assertTrue(check);
    }
    @Test
    void createCourseTitleAlreadyExisting() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void createCourseProfessorsEmpty() {
        String professors = "";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void createCourseProfessorsBadFormatted() {
        String professors = "Andrea De Lucia8 Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void createCourseScheduleEmpty() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void createCourseScheduleBadFormatted() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lunedì 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void createCourseTitleEmpty() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void createCourseTitleBadFormatted() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software!!!";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void modifyInfoCourseSuccess() {
        String newProfessors = "Andrea De Lucia, Fabio Palomba";
        String newSchedule = "Lun 09:00 - 11:00, Gio 15:30 - 18:30";

        boolean check = CourseManager.modifyInfoCourse(courseForTesting, newProfessors, newSchedule);

        assertTrue(check);
    }

    @Test
    void modifyInfoCourseProfessorsEmpty() {
        String professors = "";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void modifyInfoCourseProfessorsBadFormatted() {
        String professors = "Andrea De Lucia8 Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void modifyInfoCourseScheduleEmpty() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }

    @Test
    void modifyInfoCourseScheduleBadFormatted() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lunedì 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        boolean check = CourseManager.createCourse(professors,schedule,title);
        assertFalse(check);
    }
}