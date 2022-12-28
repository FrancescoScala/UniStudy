package it.unisa.dao;
import org.junit.jupiter.api.*;

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

    
}