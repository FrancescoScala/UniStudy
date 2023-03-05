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
        roles.add(new Role(1, "PARTECIPANTE"));

        memberForTesting = new Member(-2, email, password, name, surname, roles);
    }

    @AfterAll
    void tearDown() {
        try {
            Connection con = ConnectionPoolDB.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE user_id=?");
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
    void signupMemberSuccess() {
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

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupMemberEmailBadFormatted() {
        String email = "testemail.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupMemberEmailEmpty() {
        String email = "";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupMemberPasswordBadFormatted() {
        String email = "test@email.com";
        String password = "Password8";
        String name = "Name";
        String surname = "Surname";

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupMemberPasswordEmpty() {
        String email = "test@email.com";
        String password = "";
        String name = "Name";
        String surname = "Surname";

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupMemberNameNotValid() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name8";
        String surname = "Surname";

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupMemberNameEmpty() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "";
        String surname = "Surname";

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupMemberSurnameNotValid() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname8";

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupMemberSurnameEmpty() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "";

        boolean check = MemberManager.signupMember(email, password, name, surname);

        assertFalse(check);
    }


    //Login
    @Order(2)
    @Test
    void loginMemberSuccess() {
        Member member = MemberManager.loginMember(memberForTesting.getEmail(), memberForTesting.getPassword());

        assertEquals(memberForTesting, member);
    }

    @Test
    void loginMemberEmailNotExisting() {
        String email = "test@notExistingEmail.com";
        String password = "P@ssword8";

        Member member = MemberManager.loginMember(email, password);

        assertNull(member);
    }

/*    @Test
    void loginMemberEmailBadFormatted() {
        String email = "testemail.com";
        String password = "P@ssword8";

        Member user = MemberManager.loginMember(email, password);

        assertNull(user);
    }
*/
    @Test
    void loginMemberEmailEmpty() {
        String email = "";
        String password = "P@ssword8";

        Member member = MemberManager.loginMember(email, password);

        assertNull(member);
    }

    @Test
    void loginMemberPasswordNotExisting() {
        String email = "test@email.com";
        String password = "P@ssword1";

        Member member = MemberManager.loginMember(email, password);

        assertNull(member);
    }

   /* @Test
    void loginMemberPasswordBadFormatted() {
        String email = "test@email.com";
        String password = "P@ssword";

        Member user = MemberManager.loginMember(email, password);

        assertNull(user);
    }

    @Test
    void loginMemberPasswordMaxLenght() {
        String email = "test@email.com";
        String password = "P@ssword81458";

        Member user = MemberManager.loginMember(email, password);

        assertNull(user);
    }

    @Test
    void loginMemberPasswordMinLenght() {
        String email = "test@email.com";
        String password = "P@sswo7";

        Member user = MemberManager.loginMember(email, password);

        assertNull(user);
    }
*/
    @Test
    void loginMemberPasswordEmpty() {
        String email = "test@email.com";
        String password = "";

        Member member = MemberManager.loginMember(email, password);

        assertNull(member);
    }

    @Test
    void modifyInfoMemberSuccess()
    {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertTrue(check);
    }

    @Test
    void modifyInfoMemberSurnameBadFormatted()
    {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino89";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void modifyInfoMemberSurnameEmpty()
    {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void modifyInfoMemberNameBadFormatted()
    {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "Pinco88";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void modifyInfoMemberNameEmpty()
    {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword888";
        String name = "";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void modifyInfoMemberNewPasswordBadFormatted()
    {
        String oldPassword = "P@sswordd";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

/*    @Test
    void modifyInfoMemberNewPasswordMaxLength()
    {
        String oldPassword = "P@ssword8";
        String newPassword = "P@ssword88888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void modifyInfoMemberNewPasswordMinLength()
    {
        String oldPassword = "P@ssword8";
        String newPassword = "P@sswo8";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }
*/
    @Test
    void modifyInfoMemberNewPasswordEmpty()
    {
        String oldPassword = "P@ssword8";
        String newPassword = "";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void modifyInfoMemberOldPasswordNotValid()
    {
        String oldPassword = "P@ssword9";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

/*    @Test
    void modifyInfoMemberOldPasswordBadFormatted()
    {
        String oldPassword = "P@sswordd";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void modifyInfoMemberOldPasswordMaxLength()
    {
        String oldPassword = "P@ssword88888";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void modifyInfoMemberOldPasswordMinLength()
    {
        String oldPassword = "P@sswo8";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }
*/
    @Test
    void modifyInfoMemberOldPasswordEmpty()
    {
        String oldPassword = "";
        String newPassword = "P@ssword888";
        String name = "Pinco";
        String surname = "Pallino";

        boolean check = MemberManager.modifyInfoMember(memberForTesting, name, surname, newPassword, oldPassword);

        assertFalse(check);
    }

    @Test
    void retrieveIdMemberByEmailSuccess() {
        String email = memberForTesting.getEmail();
        int id = MemberManager.retrieveIdMemberByEmail(email);
        assertEquals(memberForTesting.getId(),id);
    }

    @Test
    void retrieveIdMemberByEmailNotValid() {
        String email = "test@otheremail.com";
        int id = MemberManager.retrieveIdMemberByEmail(email);
        assertEquals(-1,id);
    }
}