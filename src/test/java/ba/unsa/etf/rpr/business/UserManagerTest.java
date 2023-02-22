package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import ba.unsa.etf.rpr.exceptions.UserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Class used for testing UserManager class
 * @author Muaz Sikiric
 */
public class UserManagerTest {
    private final UserManager userManager = Mockito.mock(UserManager.class);
    private User admin;

    @BeforeEach
    public void setup() throws UserException {
        admin = newUser("adminuser", "Strongpass123", true);
        Mockito.doCallRealMethod().when(userManager).validateRegistration(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.doCallRealMethod().when(userManager).validateLogin(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void getUserTest() throws BookstoreException {
        User newAdmin = new User();
        newAdmin.setUsername("sudo");
        newAdmin.setPassword("aptGetInstall");
        Mockito.when(userManager.getUser(admin.getUsername(), admin.getPassword())).thenReturn(newAdmin);
        User user = userManager.getUser(admin.getUsername(), admin.getPassword());
        Assertions.assertEquals(user, newAdmin);
    }

    @Test
    public void registrationTest() {
        Assertions.assertDoesNotThrow(() -> userManager.validateRegistration(admin.getUsername(), admin.getPassword(), admin.getPassword()));

        UserException e = Assertions.assertThrows(UserException.class, () -> {
            userManager.validateRegistration("otheruser", "Strongpass123", "Strongestpass123");
        });
        Assertions.assertEquals("Passwords do not match.", e.getMessage());

        e = Assertions.assertThrows(UserException.class, () -> {
            userManager.validateRegistration("otheruser", "strongpass", "strongpass");
        });
        Assertions.assertEquals("Password needs to contain at least one uppercase letter and one number.", e.getMessage());
    }

    @Test
    public void loginTest() {
        Assertions.assertDoesNotThrow(() -> userManager.validateLogin(admin.getUsername(), admin.getPassword()));
        UserException e = Assertions.assertThrows(UserException.class, () -> {
            userManager.validateLogin("otheruser", "strongpass");
        });
        Assertions.assertEquals("Password needs to contain at least one uppercase letter and one number.", e.getMessage());

        e = Assertions.assertThrows(UserException.class, () -> {
            userManager.validateLogin("otheruser", "short");
        });
        Assertions.assertEquals("Password needs to be at least 8 characters.", e.getMessage());

        e = Assertions.assertThrows(UserException.class, () -> {
            userManager.validateLogin("short", "Strongpass123");
        });
        Assertions.assertEquals("Username needs to be at least 6 characters.", e.getMessage());
    }

    /**
     * Creates new user with given parameters
     * @return new user
     */
    private User newUser(String username, String password, boolean isAdmin) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(isAdmin);
        return user;
    }
}
