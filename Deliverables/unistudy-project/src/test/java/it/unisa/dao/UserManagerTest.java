package it.unisa.dao;
import it.unisa.beans.Role;
import it.unisa.beans.User;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    //private Connection connection;
    @BeforeEach
    void setUp() {
        //connection = ConnectionPoolDB.getConnection();
    }

    @AfterEach
    void tearDown() {
        //connection.close();
    }

    @Test
    void signupUserSuccess()
    {
        //arrange
        String email = "email";
        String password= "pass";
        String name = "name";
        String surname = "surname";
        //act
        boolean check = UserManager.signupUser(email, password, name, surname);
        //assert
        assertTrue(check);
    }

    @Test
    void signupUserAlreadyExisting()
    {
        String email = "email";
        String password= "pass";
        String name = "name";
        String surname = "surname";

        boolean check = UserManager.signupUser(email, password, name, surname);

        assertFalse(check);
    }

    @Test
    void loginUserSuccess() {
        String email = "email";
        String password= "pass";
        Set<Role> roles = new HashSet<Role>();
        roles.add(new Role(1,"PARTECIPANTE"));
        User expectedUser = new User(3,email,password,"name","surname",roles);

        User user = UserManager.loginUser(email, password);

        assertEquals(expectedUser,user);
    }

    @Test
    void loginUserError() {
        String email = "email1";
        String password= "pass";

        User user = UserManager.loginUser(email, password);

        assertNull(user);
    }

}