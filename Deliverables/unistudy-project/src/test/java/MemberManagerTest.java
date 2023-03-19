import user.beans.Member;
import user.beans.Role;
import connection.ConnectionPoolDB;
import org.junit.jupiter.api.*;
import user.manager.MemberManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberManagerTest {
    private Member memberForTesting;

    @BeforeAll
    void setUp() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";
        Set<Role> roles = new HashSet<Role>();
        roles.add(new Role(2, "AMMINISTRATORE"));
        memberForTesting = new Member(-2, email, password, name, surname, roles);
    }

    @AfterAll
    void tearDown() {
        try {
            Connection con = ConnectionPoolDB.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM has_role WHERE role_id=2 AND user_id=?");
            ps.setInt(1, memberForTesting.getId());
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("DELETE FROM user WHERE user_id=?");
            ps.setInt(1, memberForTesting.getId());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Order(1)
    @Test
    void signupMemberSuccess() throws SQLException {
        boolean check = MemberManager.signupMember(memberForTesting.getEmail(), memberForTesting.getPassword(), memberForTesting.getName(), memberForTesting.getSurname());
        memberForTesting.setId(MemberManager.retrieveIdMemberByEmail(memberForTesting.getEmail()));
        assertTrue(check);
    }

    @Test
    void signupMemberAlreadyExisting() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.signupMember(email, password, name, surname);
        });
    }

    @Test
    void signupMemberEmailBadFormatted() {
        String email = "testemail.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.signupMember(email, password, name, surname);
        });
    }

    @Test
    void signupMemberPasswordBadFormatted() {
        String email = "test@email.com";
        String password = "Password8";
        String name = "Name";
        String surname = "Surname";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.signupMember(email, password, name, surname);
        });
    }

    @Test
    void signupMemberNameBadFormatted() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name8";
        String surname = "Surname";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.signupMember(email, password, name, surname);
        });
    }

    @Test
    void signupMemberSurnameBadFormatted() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname8";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.signupMember(email, password, name, surname);
        });
    }

    //Login
    @Order(2)
    @Test
    void loginMemberSuccess() throws SQLException, RuntimeException {
        Connection con = ConnectionPoolDB.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO has_role(user_id,role_id) VALUES (?,2)");
        ps.setInt(1, memberForTesting.getId());
        ps.executeUpdate();
        ps.close();
        con.close();

        Member member = MemberManager.loginMember(memberForTesting.getEmail(), memberForTesting.getPassword());

        assertEquals(memberForTesting, member);
    }

    @Test
    void loginMemberEmailNotExisting() {
        String email = "test@notExistingEmail.com";
        String password = "P@ssword8";

        assertThrows(RuntimeException.class, () -> {
            Member member = MemberManager.loginMember(email, password);
        });
    }

    @Test
    void loginMemberEmailBadFormatted() {
        String email = "testemail.com";
        String password = "P@ssword8";

        assertThrows(RuntimeException.class, () -> {
            Member member = MemberManager.loginMember(email, password);
        });
    }


    @Test
    void loginMemberPasswordNotExisting() {
        String email = "test@email.com";
        String password = "P@ssword1";

        assertThrows(RuntimeException.class, () -> {
            Member member = MemberManager.loginMember(email, password);
        });
    }

    @Test
    void loginMemberPasswordBadFormatted() {
        String email = "test@email.com";
        String password = "Password8";

        assertThrows(RuntimeException.class, () -> {
            Member member = MemberManager.loginMember(email, password);
        });
    }

    @Test
    void modifyInfoMemberSuccess() throws SQLException {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertTrue(check);
    }

    @Test
    void modifyInfoMemberSurnameBadFormatted() {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino89";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);
        });
    }

    @Test
    void modifyInfoMemberNameBadFormatted() {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "Pinco88";
        String surname = "Pallino";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);
        });
    }

    @Test
    void modifyInfoMemberNewPasswordBadFormatted() {
        String oldPassword = "P@ssword8";
        String newPassword = "Password8";
        String name = "Pinco";
        String surname = "Pallino";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);
        });
    }

    @Test
    void modifyInfoMemberOldPasswordNotValid() {
        String oldPassword = "P@ssword9";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);
        });
    }

    @Test
    void modifyInfoMemberOldPasswordBadFormatted() {
        String oldPassword = "Password8";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        assertThrows(RuntimeException.class, () -> {
            MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);
        });
    }

    @Test
    void modifyInfoMemberNotValid() {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";
        Member member = new Member(-2, "test@emailNotExisting.com", "P@ssword8", "Name", "Surname", new HashSet<Role>());

        assertThrows(RuntimeException.class, () -> {
            MemberManager.modifyInfoMember(member, name, surname, newPassword, oldPassword);
        });
    }
    @Test
    void retrieveIdMemberByEmailSuccess() {
        String email = memberForTesting.getEmail();
        int id = MemberManager.retrieveIdMemberByEmail(email);
        assertEquals(memberForTesting.getId(),id);
    }

    @Test
    void retrieveIdMemberByEmailNotValid() {
        String email = "";
        int id = MemberManager.retrieveIdMemberByEmail(email);
        assertEquals(-1, id);
    }
}