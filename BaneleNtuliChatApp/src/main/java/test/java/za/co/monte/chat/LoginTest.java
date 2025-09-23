package test.java.za.co.monte.chat;

import org.junit.jupiter.api.Test;
import za.co.monte.chat.Login;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    // ---------- assertEquals-style checks ----------

    @Test
    void usernameCorrectlyFormatted() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");
        assertEquals(true, l.checkUserName());
    }

    @Test
    void usernameIncorrectlyFormatted() {
        Login l = new Login("Kyl", "Smith", "kyle!!!!!!", "Ch&&sec@ke99!", "+2783986976");
        assertEquals(false, l.checkUserName());
    }

    @Test
    void passwordMeetsComplexity() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");
        assertEquals(true, l.checkPasswordComplexity());
    }

    @Test
    void passwordDoesNotMeetComplexity() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "password", "+2783986976");
        assertEquals(false, l.checkPasswordComplexity());
    }

    @Test
    void cellphoneCorrectlyFormatted() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");
        assertEquals(true, l.checkCellPhoneNumber());
    }

    @Test
    void cellphoneIncorrectlyFormatted() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "0896653");
        assertEquals(false, l.checkCellPhoneNumber());
    }

    // ---------- assertTrue / assertFalse for login ----------

    @Test
    void loginSuccessful() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");
        assertTrue(l.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    void loginFailed() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");
        assertFalse(l.loginUser("kyl_1", "wrongPass"));
    }

    @Test
    void returnLoginStatus_Success() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");
        String expected = "Welcome Kyl, Smith it is great to see you again.";
        assertEquals(expected, l.returnLoginStatus(true));
    }

    @Test
    void returnLoginStatus_Fail() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");
        String expected = "Username or password incorrect, please try again.";
        assertEquals(expected, l.returnLoginStatus(false));
    }
}
