package it.unisa.dao;

import it.unisa.beans.Course;
import it.unisa.beans.Note;
import it.unisa.beans.Notice;
import it.unisa.db.ConnectionPoolDB;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        courseForTesting = new Course(-2, professors,schedule,title,notices,notes);
        CourseManager.createCourse(courseForTesting.getProfessors(),courseForTesting.getTimeSchedule(),courseForTesting.getTitle());
        courseForTestingId = CourseManager.retrieveIdCourseByTitle(courseForTesting.getTitle());
        courseForTesting.setId(courseForTestingId);

        noticeForTesting = new Notice(-3,"Comunicazione data inizio tutorato",new Timestamp(System.currentTimeMillis()),"Si comunica che dalla prossima settimana inizierà il tutoraggio per il presente corso");
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


    @Test
    @Order(1)
    void createNoticeSuccess() {
        boolean check = NoticeManager.createNotice(noticeForTesting.getTitle(),noticeForTesting.getCreationDate(),noticeForTesting.getDescription(),courseForTesting);
        assertTrue(check);
    }

    @Test
    void createNoticeCourseNotValid() {
        courseForTesting.setId(-1);

        boolean check = NoticeManager.createNotice(noticeForTesting.getTitle(),noticeForTesting.getCreationDate(),noticeForTesting.getDescription(),courseForTesting);
        courseForTesting.setId(courseForTestingId);

        assertFalse(check);
    }

    @Test
    void createNoticeDescrMaxLength() {
        String descriptionForTesting= "Risale al 1944 la costituzione di un istituto universitario di magistero nella città, fortemente voluto da Giovanni Cuomo. Esso divenne statale nel 1968, trasformandosi nella facoltà di magistero della costituenda Università degli Studi di Salerno. Nel 1969 la facoltà di magistero divenne facoltà di lettere e filosofia, affiancata, dal 1970, dalla facoltà di economia e commercio. Nel 1972 furono fondate le facoltà di scienze matematiche, fisiche e naturali e di giurisprudenza, nel 1983 il corso completo di ingegneria. Nel 1988 l'università fu spostata dal centro urbano del comune capoluogo alle nuove strutture del comune di Fisciano, ai margini della provincia. Nel 1991 fu aggiunta la facoltà di farmacia, nel 1992 quella di scienze politiche, nel 1996 quella di lingue e letterature straniere e nel 2006 la facoltà di medicina e chirurgia.[1] Nel 2006 avviò i propri corsi la scuola di giornalismo di Salerno, riconosciuta dall'Ordine nazionale dei giornalisti[2], mentre nel 2014 furono attivate le prime tre scuole di specializzazione dell'area medica presso l'Azienda Ospedaliera Universitaria San Giovanni di Dio e Ruggi D'Aragona";
        boolean check = NoticeManager.createNotice(noticeForTesting.getTitle(),noticeForTesting.getCreationDate(),descriptionForTesting,courseForTesting);

        assertFalse(check);
    }

    @Test
    void createNoticeDescrEmpty() {
        boolean check = NoticeManager.createNotice(noticeForTesting.getTitle(),noticeForTesting.getCreationDate(),"",courseForTesting);

        assertFalse(check);
    }

    @Test
    void createNoticeTitleEmpty() {
        boolean check = NoticeManager.createNotice("",noticeForTesting.getCreationDate(),noticeForTesting.getDescription(),courseForTesting);

        assertFalse(check);
    }

    @Test
    void createNoticeTitleBadFormatted() {
        String titleForTesting = "%$#";
        boolean check = NoticeManager.createNotice(titleForTesting,noticeForTesting.getCreationDate(),noticeForTesting.getDescription(),courseForTesting);

        assertFalse(check);
    }

    //controllo max length 50

    @Test
    void retrieveNoticesByCourseId() {

    }
}
