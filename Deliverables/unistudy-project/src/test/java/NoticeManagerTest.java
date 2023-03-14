import course.beans.Course;
import course.beans.Note;
import course.beans.Notice;
import connection.ConnectionPoolDB;
import course.manager.CourseManager;
import course.manager.NoticeManager;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NoticeManagerTest {
    private Course courseForTesting;
    private int courseForTestingId;
    private Notice noticeForTesting;

    @BeforeAll
    void setUp() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";
        Set<Note> notes = new HashSet<Note>();
        Set<Notice> notices = new HashSet<Notice>();
        courseForTesting = new Course(-2, professors, schedule, title, notices, notes);
        CourseManager.createCourse(courseForTesting.getProfessors(), courseForTesting.getTimeSchedule(), courseForTesting.getTitle());
        courseForTestingId = CourseManager.retrieveIdCourseByTitle(courseForTesting.getTitle());
        courseForTesting.setId(courseForTestingId);

        noticeForTesting = new Notice(-3, "Comunicazione data inizio tutorato", new Timestamp(System.currentTimeMillis()), "Si comunica che dalla prossima settimana inizierà il tutoraggio per il presente corso");
    }

    @AfterAll
    void tearDown() {
        try {
            Connection con = ConnectionPoolDB.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM course WHERE course_id=?");
            ps.setInt(1, courseForTesting.getId());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @Order(1)
    void createNoticeSuccess() {
        boolean check = NoticeManager.createNotice(noticeForTesting.getTitle(), noticeForTesting.getCreationDate(), noticeForTesting.getDescription(), courseForTesting.getId());
        courseForTesting = CourseManager.retrieveCourseById(courseForTestingId);
        assertTrue(check);
    }

    @Test
    void createNoticeCourseNotValid() {
       // courseForTesting.setId(-1);

        boolean check = NoticeManager.createNotice(noticeForTesting.getTitle(), noticeForTesting.getCreationDate(), noticeForTesting.getDescription(),-1);
        courseForTesting.setId(courseForTestingId);

        assertFalse(check);
    }

    @Test
    void createNoticeTitleBadFormatted() {
        String titleForTesting = "";
        boolean check = NoticeManager.createNotice(titleForTesting, noticeForTesting.getCreationDate(), noticeForTesting.getDescription(), courseForTesting.getId());

        assertFalse(check);
    }

    @Test
    void createNoticeDescrBadFormatted() {
        String descriptionForTesting = "";
        boolean check = NoticeManager.createNotice(noticeForTesting.getTitle(), noticeForTesting.getCreationDate(), descriptionForTesting, courseForTesting.getId());

        assertFalse(check);
    }


/*
    @Test
    void retrieveNoticesByCourseIdSuccess() {
        Set<Notice> notices = NoticeManager.retrieveNoticesByCourseId(courseForTesting.getId());
        assertEquals(courseForTesting.getNotices(), notices);
    }*/


/*    @Test
    void retrieveNoticesByCourseIdNotValid() {
        Set<Notice> notices = NoticeManager.retrieveNoticesByCourseId(-2);

        assertNull(notices);
    }*/
}
