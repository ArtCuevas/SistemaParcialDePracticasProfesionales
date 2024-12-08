import com.sppp.dao.UserDAO;
import com.sppp.dao.UserDAOImp;
import com.sppp.model.User;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAOImpTest {

    private UserDAO userDAO;

    @BeforeAll
    void setup() {
        userDAO = new UserDAOImp();
    }

    @Test
    void createUserTest() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        assertDoesNotThrow(() -> userDAO.createUser(user));
        assertTrue(user.getIduser() > 0, "El ID del usuario debería haberse generado.");
    }

    @Test
    void readUserTest() throws SQLException {
        User user = new User();
        user.setUsername("readUser");
        user.setPassword("readPassword");
        userDAO.createUser(user);

        UserDAO rUser = new UserDAOImp();
        User retrievedUser = rUser.readUser(user.getIduser());
        assertNotNull(retrievedUser, "El usuario debería haberse recuperado.");
        assertEquals("readUser", retrievedUser.getUsername(), "El nombre de usuario no coincide.");
        assertEquals("readPassword", retrievedUser.getPassword(), "La contraseña no coincide.");
    }

    @Test
    void updateUserTest() throws SQLException {
        User user = new User();
        user.setUsername("updateUser");
        user.setPassword("oldPassword");
        userDAO.createUser(user);

        user.setUsername("updatedUser");
        user.setPassword("newPassword");
        assertDoesNotThrow(() -> userDAO.updateUser(user));

        UserDAO rUser = new UserDAOImp();

        User updatedUser = rUser.readUser(user.getIduser());
        assertNotNull(updatedUser, "El usuario actualizado debería existir.");
        assertEquals("updatedUser", updatedUser.getUsername(), "El nombre de usuario debería haberse actualizado.");
        assertEquals("newPassword", updatedUser.getPassword(), "La contraseña debería haberse actualizado.");
    }

    @Test
    void deleteUserTest() throws SQLException {
        User user = new User();
        user.setUsername("deleteUser");
        user.setPassword("deletePassword");
        userDAO.createUser(user);

        assertDoesNotThrow(() -> userDAO.deleteUser(user));

        User deletedUser = userDAO.readUser(user.getIduser());
        assertNull(deletedUser.getUsername(), "El usuario debería haberse eliminado.");
    }

    @Test
    void getAllUsersTest() throws SQLException {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        userDAO.createUser(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");
        userDAO.createUser(user2);

        List<User> users = userDAO.getAllUsers();
        assertNotNull(users, "La lista de usuarios no debería ser nula.");
        assertTrue(users.size() >= 2, "La lista debería contener al menos 2 usuarios.");
    }
}
