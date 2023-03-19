import course.beans.Course;
import course.beans.Note;
import course.beans.Notice;
import course.manager.CourseManager;
import course.manager.NoteManager;
import course.manager.NoticeManager;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import user.manager.MemberManager;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


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

        courseForTesting = new Course(-2, professors, schedule, title, notices, notes);
    }

    @AfterAll
    void deleteCourseSuccess() throws SQLException {
        boolean check = CourseManager.deleteCourse(courseForTesting.getId());
        assertTrue(check);
    }

    @Order(1)
    @Test
    void createCourseSuccess() throws SQLException {
        boolean check = CourseManager.createCourse(courseForTesting.getProfessors(), courseForTesting.getTimeSchedule(), courseForTesting.getTitle());
        courseForTesting.setId(CourseManager.retrieveIdCourseByTitle(courseForTesting.getTitle()));
        assertTrue(check);
    }

    @Test
    void createCourseProfessorsBadFormatted() {
        String professors = "Andrea De Lucia8 Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        assertThrows(RuntimeException.class, () -> {
            CourseManager.createCourse(professors, schedule, title);
        });
    }

    @Test
    void createCourseScheduleBadFormatted() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lunedì 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        assertThrows(RuntimeException.class, () -> {
            CourseManager.createCourse(professors, schedule, title);
        });
    }

    @Test
    void createCourseTitleBadFormatted() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "";

        assertThrows(RuntimeException.class, () -> {
            CourseManager.createCourse(professors, schedule, title);
        });
    }

    @Test
    void createCourseTitleAlreadyExisting() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";

        assertThrows(RuntimeException.class, () -> {
            CourseManager.createCourse(professors, schedule, title);
        });
    }

    @Test
    void modifyInfoCourseSuccess() throws SQLException {
        String newProfessors = "Andrea De Lucia, Fabio Palomba";
        String newSchedule = "Lun 09:00 - 11:00, Gio 15:30 - 18:30";

        boolean check = CourseManager.modifyInfoCourse(courseForTesting, newProfessors, newSchedule);

        assertTrue(check);
    }

    @Test
    void modifyInfoCourseProfessorsBadFormatted() {
        String professors = "Andrea De Lucia8 Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";

        assertThrows(RuntimeException.class, () -> {
            CourseManager.modifyInfoCourse(courseForTesting, professors, schedule);
        });
    }

    @Test
    void modifyInfoCourseScheduleBadFormatted() {
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lunedì 09:00 - 11:00, Gio 15:00 - 18:00";

        assertThrows(RuntimeException.class, () -> {
            CourseManager.modifyInfoCourse(courseForTesting, professors, schedule);
        });
    }

    @Test
    void modifyInfoNotExistingCourse() {
        String newProfessors = "Andrea De Lucia, Fabio Palomba";
        String newSchedule = "Lun 09:00 - 11:00, Gio 15:30 - 18:30";

        assertThrows(RuntimeException.class, () -> {
            CourseManager.modifyInfoCourse(new Course(-2, "Andrea De Lucia, Fabio Palomba", "Lun 09:00 - 11:00, Gio 15:30 - 18:30", "MATERIA", new HashSet<>(), new HashSet<>()), newProfessors, newSchedule);
        });
    }

    @Test
    void retrieveCourseByIdSuccess() {
        int id = courseForTesting.getId();

        MockedStatic<NoticeManager> noticeMock = Mockito.mockStatic(NoticeManager.class);
        MockedStatic<NoteManager> noteMock = Mockito.mockStatic(NoteManager.class);

        noticeMock.when(() -> NoticeManager.retrieveNoticesByCourseId(id))
                .thenReturn(new HashSet<Notice>());

        noteMock.when(() -> NoteManager.retrieveNotesByCourseId(id))
                .thenReturn(new HashSet<Note>());

        Course course = CourseManager.retrieveCourseById(id);
        noticeMock.close();
        noteMock.close();
        assertEquals(courseForTesting, course);

    }

    @Test
    void retrieveCourseByIdNotValid() {
        int id = -3;

        Course course = CourseManager.retrieveCourseById(id);
        assertNull(course);
    }

    @Test
    void retrieveAllSuccess() {
        Set<Course> courses = CourseManager.retrieveAll();
        assertTrue(courses.size() != 0);
    }


    @Test
    void retrieveIdCourseByTitleSuccess() {
        String title = courseForTesting.getTitle();
        int id = CourseManager.retrieveIdCourseByTitle(title);
        assertEquals(courseForTesting.getId(), id);
    }

    @Test
    void retrieveIdCourseByTitleNotValid() {
        String title = "";
        int id = CourseManager.retrieveIdCourseByTitle(title);
        assertEquals(-1, id);
    }
}
