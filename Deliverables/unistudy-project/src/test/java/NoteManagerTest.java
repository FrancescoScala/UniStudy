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
        authorForTesting = new Member(-1, email, password, name, surname, roles);

        //save the author in the db. Take the real id
        MemberManager.signupMember(email, password, name, surname);
        authorForTesting.setId(MemberManager.retrieveIdMemberByEmail(email));
        //create the note for testing, using the id of the author just created
        noteForTesting = new Note(-1,"descrizione",new Timestamp(System.currentTimeMillis()),"/ciao/filepath.img","title",
                authorForTesting.getId(),
                authorForTesting.getName()+" "+authorForTesting.getSurname());
    }

    @AfterAll
    void tearDown() throws SQLException {
        Connection con = ConnectionPoolDB.getConnection();

        PreparedStatement ps2 = con.prepareStatement("DELETE FROM course WHERE course_id=?");
        ps2.setInt(1, courseForTesting.getId());
        ps2.executeUpdate();
        ps2.close();

        PreparedStatement ps1 = con.prepareStatement("DELETE FROM user WHERE user_id=?");
        ps1.setInt(1, authorForTesting.getId());
        ps1.executeUpdate();
        ps1.close();

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

    @Test
    void createNotePathBadFormatted()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(),noteForTesting.getCreationDate(),"badFormattedPath",noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),courseForTesting);

        assertFalse(check);
    }

    //checks on the description still to implement in NoteManager
    @Test
    void createNoteDescriptionMaxLength()
    {
        String descriptionForTesting= "Risale al 1944 la costituzione di un istituto universitario di magistero nella città, fortemente voluto da Giovanni Cuomo. Esso divenne statale nel 1968, trasformandosi nella facoltà di magistero della costituenda Università degli Studi di Salerno. Nel 1969 la facoltà di magistero divenne facoltà di lettere e filosofia, affiancata, dal 1970, dalla facoltà di economia e commercio. Nel 1972 furono fondate le facoltà di scienze matematiche, fisiche e naturali e di giurisprudenza, nel 1983 il corso completo di ingegneria. Nel 1988 l'università fu spostata dal centro urbano del comune capoluogo alle nuove strutture del comune di Fisciano, ai margini della provincia. Nel 1991 fu aggiunta la facoltà di farmacia, nel 1992 quella di scienze politiche, nel 1996 quella di lingue e letterature straniere e nel 2006 la facoltà di medicina e chirurgia.[1] Nel 2006 avviò i propri corsi la scuola di giornalismo di Salerno, riconosciuta dall'Ordine nazionale dei giornalisti[2], mentre nel 2014 furono attivate le prime tre scuole di specializzazione dell'area medica presso l'Azienda Ospedaliera Universitaria San Giovanni di Dio e Ruggi D'Aragona";
        boolean check = NoteManager.createNote(descriptionForTesting,noteForTesting.getCreationDate(), noteForTesting.getFilePath(), noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),courseForTesting);

        assertFalse(check);
    }

    @Test
    void createNoteDescriptionEmpty()
    {
        boolean check = NoteManager.createNote("",noteForTesting.getCreationDate(), noteForTesting.getFilePath(), noteForTesting.getTitle(),
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),courseForTesting);

        assertFalse(check);
    }

    //checks on the title still to implement in NoteManager
    @Test
    void createNoteTitleBadFormatted()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(), noteForTesting.getCreationDate(), noteForTesting.getFilePath(),"_$!&",
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),courseForTesting);

        assertFalse(check);
    }

    @Test
    void createNoteTitleEmpty()
    {
        boolean check = NoteManager.createNote(noteForTesting.getDescription(), noteForTesting.getCreationDate(), noteForTesting.getFilePath(),"",
                noteForTesting.getAuthorId(), noteForTesting.getAuthorInfo(),courseForTesting);

        assertFalse(check);
    }

    @Test
    void retrieveNotesByCourseIdSuccess() {
        Set<Note> notes = NoteManager.retrieveNotesByCourseId(courseForTesting.getId());
        assertEquals(courseForTesting.getNotes(),notes);
    }

/*    @Test
    void retrieveNotesByCourseIdNotValid()
    {
        Set<Note> notes = NoteManager.retrieveNotesByCourseId(-1);
        assertNull(notes);
    }*/
}
