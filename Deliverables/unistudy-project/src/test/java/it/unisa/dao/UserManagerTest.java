package it.unisa.dao;

import it.unisa.beans.Role;
import it.unisa.beans.User;
import it.unisa.db.ConnectionPoolDB;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserManagerTest {
    private User userForTesting;

    @BeforeAll
    void setUp() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";
        Set<Role> roles = new HashSet<Role>();
        roles.add(new Role(1, "PARTECIPANTE"));

        userForTesting = new User(-1, email, password, name, surname, roles);
    }

    @AfterAll
    void tearDown() {
        try {
            Connection con = ConnectionPoolDB.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE user_id=?");
            ps.setInt(1, userForTesting.getId());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Order(1)
    @Test
    void signupUserSuccess() {
        boolean check = UserManager.signupUser(userForTesting.getEmail(), userForTesting.getPassword(), userForTesting.getName(), userForTesting.getSurname());
        userForTesting.setId(UserManager.retrieveIdUserByEmail(userForTesting.getEmail()));
        assertTrue(check);
    }

    @Test
    void signupUserAlreadyExisting() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserEmailBadFormatted() {
        String email = "testemail.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserEmailEmpty() {
        String email = "";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserPasswordBadFormatted() {
        String email = "test@email.com";
        String password = "Password8";
        String name = "Name";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserPasswordMaxLenght() {
        String email = "test@email.com";
        String password = "P@ssword81210";
        String name = "Name";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserPasswordMinLenght() {
        String email = "test@email.com";
        String password = "P@sswo7";
        String name = "Name";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserPasswordEmpty() {
        String email = "test@email.com";
        String password = "";
        String name = "Name";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserNameNotValid() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name8";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserNameEmpty() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "";
        String surname = "Surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserSurnameNotValid() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname8";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void signupUserSurnameEmpty() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }


    //Login
    @Test
    void loginUserSuccess() {
        User user = UserManager.loginUser(userForTesting.getEmail(), userForTesting.getPassword());

        assertEquals(userForTesting, user);
    }

    @Test
    void loginUserEmailNotExisting() {
        String email = "test@notExistingEmail.com";
        String password = "P@ssword8";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

    @Test
    void loginUserEmailBadFormatted() {
        String email = "testemail.com";
        String password = "P@ssword8";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

    @Test
    void loginUserEmailEmpty() {
        String email = "";
        String password = "P@ssword8";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

    @Test
    void loginUserPasswordNotExisting() {
        String email = "test@email.com";
        String password = "P@ssword1";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

    @Test
    void loginUserPasswordBadFormatted() {
        String email = "test@email.com";
        String password = "P@ssword";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

    @Test
    void loginUserPasswordMaxLenght() {
        String email = "test@email.com";
        String password = "P@ssword81458";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

    @Test
    void loginUserPasswordMinLenght() {
        String email = "test@email.com";
        String password = "P@sswo7";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

    @Test
    void loginUserPasswordEmpty() {
        String email = "test@email.com";
        String password = "";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

}