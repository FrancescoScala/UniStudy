import user.beans.Member;
import user.beans.Role;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberTest {
    private Member memberForTesting;
    @BeforeAll
    void setUp() {
        String email = "test@email.com";
        String password = "P@ssword8";
        String name = "Name";
        String surname = "Surname";
        Set<Role> roles = new HashSet<Role>();
        roles.add(new Role(1, "PARTECIPANTE"));
        roles.add(new Role(2,"GESTOREUTENTI"));

        memberForTesting = new Member(-1, email, password, name, surname, roles);
    }
    @AfterAll
    void tearDown() {
        memberForTesting =null;
    }
    @Test
    void checkRoleSuccess() {
        Role role = new Role(1,"PARTECIPANTE");
        boolean check = memberForTesting.checkRole(role);
        assertTrue(check);
    }

    @Test
    void checkRoleFail() {
        Role role = new Role(3,"AMMINISTRATORE");

        boolean check = memberForTesting.checkRole(role);
        assertFalse(check);
    }
}
