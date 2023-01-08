package it.unisa.dao;


import it.unisa.beans.*;
import it.unisa.db.ConnectionPoolDB;
import org.junit.jupiter.api.*;

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
    private User authorForTesting;
    private Note noteForTesting;
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
        Set<Role> roles = new HashSet<Role>();
        roles.add(new Role(1, "PARTECIPANTE"));
        authorForTesting = new User(-1, email, password, name, surname, roles);

        //save the author in the db. Take the real id
        UserManager.signupUser(email, password, name, surname);
        authorForTesting.setId(UserManager.retrieveIdUserByEmail(email));
        //create the note for testing, using the id of the author just created
        noteForTesting = new Note(-1,"descrizione",new Timestamp(System.currentTimeMillis()),"filepath.img","title",
                authorForTesting.getId(),
                authorForTesting.getName()+" "+authorForTesting.getSurname());
    }

    @AfterAll
    void tearDown() throws SQLException {
        Connection con = ConnectionPoolDB.getConnection();

        PreparedStatement ps = con.prepareStatement("DELETE FROM note WHERE note_id=?");
        ps.setInt(1, noteForTesting.getId());
        ps.executeUpdate();
        ps.close();

        PreparedStatement ps1 = con.prepareStatement("DELETE FROM user WHERE user_id=?");
        ps1.setInt(1, authorForTesting.getId());
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
    void createNoteSuccess()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(),noteForTesting.getCreationDate(),noteForTesting.getFilePath(),noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),courseForTesting);

        assertTrue(check);
    }

    @Test
    void createNoteCourseNull() {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(),noteForTesting.getCreationDate(),noteForTesting.getFilePath(),noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),null);

        assertFalse(check);
    }

    @Test
    void createNoteAuthorInfoBadFormatted()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(),noteForTesting.getCreationDate(),noteForTesting.getFilePath(),noteForTesting.getTitle(),
                noteForTesting.getAuthorId(),"12315",courseForTesting);

        assertFalse(check);
    }

    @Test
    void createNoteAuthorInfoEmpty()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(),noteForTesting.getCreationDate(),noteForTesting.getFilePath(),noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), "",courseForTesting);

        assertFalse(check);
    }

    @Test
    void createNoteAuthorIdNotValid()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(),noteForTesting.getCreationDate(),noteForTesting.getFilePath(),noteForTesting.getTitle(),
                -1, noteForTesting.getAuthorInfo(),courseForTesting);

        assertFalse(check);
    }


    //still to implement in NoteManager
    /*@Test
    void createNotePathBadFormatted()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(),noteForTesting.getCreationDate(),"badFormattedPath",noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),courseForTesting);

        assertTrue(check);
    }*/

    //still to implement in NoteManager
    /*@Test
    void createNoteDateBadFormatted()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(),noteForTesting.getCreationDate(),"badFormattedPath",noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),courseForTesting);

        assertTrue(check);
    }*/

    //checks on the description still to implement in NoteManager

    //checks on the title still to implement in NoteManager

    @Test
    void retrieveNotesByCourseIdSuccess() {
        Set<Note> notes = NoteManager.retrieveNotesByCourseId(courseForTesting.getId());
        assertEquals(courseForTesting.getNotes(),notes);
    }

    @Test
    void retrieveNotesByCourseIdNotValid()
    {
        Set<Note> notes = NoteManager.retrieveNotesByCourseId(-1);
        assertNull(notes);
    }
}
