import course.beans.Course;
import course.beans.Note;
import course.beans.Notice;
import course.manager.CourseManager;
import course.manager.NoteManager;
import connection.ConnectionPoolDB;
import org.junit.jupiter.api.*;
import user.beans.Member;
import user.beans.Role;
import user.manager.MemberManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NoteManagerTest {
    private Member authorForTesting;
    private Note noteForTesting;
    private Course courseForTesting;
    private int courseForTestingId;

    @BeforeAll
    void setUp() throws SQLException {
        // create the course for testing
        String professors = "Andrea De Lucia, Vittorio Scarano";
        String schedule = "Lun 09:00 - 11:00, Gio 15:00 - 18:00";
        String title = "Ingegneria del software";
        Set<Note> notes = new HashSet<Note>();
        Set<Notice> notices = new HashSet<Notice>();
        courseForTesting = new Course(-2, professors, schedule, title, notices, notes);

        // save the course in the db. Take the real id
        CourseManager.createCourse(courseForTesting.getProfessors(), courseForTesting.getTimeSchedule(), courseForTesting.getTitle());
        courseForTestingId = CourseManager.retrieveIdCourseByTitle(courseForTesting.getTitle());
        courseForTesting.setId(courseForTestingId);

        // create the author for testing
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";
        Set<Role> roles = new HashSet<Role>();
        authorForTesting = new Member(-1, email, password, name, surname, roles);

        //save the author in the db. Take the real id
        MemberManager.signupMember(email, password, name, surname);
        authorForTesting.setId(MemberManager.retrieveIdMemberByEmail(email));

        //create the note for testing, using the id of the author just created
        noteForTesting = new Note(-1, "descrizione", new Timestamp(System.currentTimeMillis()), "/ciao/filepath.img", "title",
                authorForTesting.getId(),
                authorForTesting.getName() + " " + authorForTesting.getSurname());
    }

    @AfterAll
    void tearDown() throws SQLException {
        boolean check = NoteManager.deleteNote(noteForTesting.getId());
        Connection con = ConnectionPoolDB.getConnection();

        CourseManager.deleteCourse(courseForTestingId);

        PreparedStatement ps1 = con.prepareStatement("DELETE FROM user WHERE user_id=?");
        ps1.setInt(1, authorForTesting.getId());
        ps1.executeUpdate();
        ps1.close();

        con.close();
        assertTrue(check);
    }

    @Order(1)
    @Test
    void createNoteSuccess() throws SQLException {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(), noteForTesting.getCreationDate(), noteForTesting.getFilePath(), noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(), courseForTesting.getId());

        assertTrue(check);
    }

    @Test
    void createNoteAuthorInfoBadFormatted() {
        assertThrows(RuntimeException.class, () -> {
            NoteManager.createNote(noteForTesting.getDescription(), noteForTesting.getCreationDate(), noteForTesting.getFilePath(), noteForTesting.getTitle(),
                    noteForTesting.getAuthorId(), "12315", courseForTesting.getId());
        });
    }

    @Test
    void createNoteAuthorIdNotValid() {
        assertThrows(SQLException.class, () -> {
            NoteManager.createNote(noteForTesting.getDescription(), noteForTesting.getCreationDate(), noteForTesting.getFilePath(), noteForTesting.getTitle(),
                    -1, noteForTesting.getAuthorInfo(), courseForTesting.getId());
        });
    }

    @Test
    void createNoteCourseIdNotValid() {
        assertThrows(SQLException.class, () -> {
            NoteManager.createNote(noteForTesting.getDescription(), noteForTesting.getCreationDate(), noteForTesting.getFilePath(), noteForTesting.getTitle(),
                    noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(), -1);
        });
    }

    @Test
    void createNotePathBadFormatted() {
        assertThrows(RuntimeException.class, () -> {
            NoteManager.createNote(noteForTesting.getDescription(), noteForTesting.getCreationDate(), "badFormattedPath", noteForTesting.getTitle(),
                    noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(), courseForTesting.getId());
        });
    }

    @Test
    void createNoteDescriptionBadFormatted() {
        assertThrows(RuntimeException.class, () -> {
            NoteManager.createNote("", noteForTesting.getCreationDate(), noteForTesting.getFilePath(), noteForTesting.getTitle(),
                    noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(), courseForTesting.getId());
        });
    }

    @Test
    void createNoteTitleBadFormatted() {
        assertThrows(RuntimeException.class, () -> {
            NoteManager.createNote(noteForTesting.getDescription(), noteForTesting.getCreationDate(), noteForTesting.getFilePath(), "",
                    noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(), courseForTesting.getId());
        });
    }

    @Test
    void retrieveNotesByCourseIdSuccess() throws SQLException {
        Set<Note> notes = NoteManager.retrieveNotesByCourseId(courseForTesting.getId());
        courseForTesting = CourseManager.retrieveCourseById(courseForTestingId);
        assertEquals(courseForTesting.getNotes(), notes);
    }
}
