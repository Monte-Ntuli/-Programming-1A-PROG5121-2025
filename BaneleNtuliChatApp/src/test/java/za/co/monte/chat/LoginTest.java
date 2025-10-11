/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package za.co.monte.chat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author E7240
 */
public class LoginTest {
    
    // ---- Happy path: all three validations pass and registerUser prints 3 success lines ----
    @Test
    @DisplayName("Registration success: username, password, cellphone all valid")
    void registrationSuccessAllValid() {
        Login l = new Login(
                "Kyl",                    // first name (dialog input)
                "Smith",                  // last name
                "kyl_1",                  // contains "_" and <= 5 chars
                "Ch&&sec@ke99!",          // >=8, uppercase, number, special
                "+2783986976"             // SA intl format +27 + 9 digits
        );

        assertTrue(l.checkUserName());
        assertTrue(l.checkPasswordComplexity());
        assertTrue(l.checkCellPhoneNumber());

        String expected =
                "Username successfully captured.\n" +
                "Password successfully captured.\n" +
                "Cell number successfully captured.";

        assertEquals(expected, l.registerUser());
    }

    // ---- Username failure branch (matches rubric wording) ----
    @Test
    @DisplayName("Registration failure: username incorrectly formatted")
    void registrationFailBadUsername() {
        Login l = new Login("Kyl", "Smith", "kyle!!!!!!", "Ch&&sec@ke99!", "+2783986976");

        assertFalse(l.checkUserName());              // dialog would loop
        assertTrue(l.checkPasswordComplexity());     // still valid
        assertTrue(l.checkCellPhoneNumber());        // still valid

        String expected =
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(expected, l.registerUser());
    }

    // ---- Password failure branch (matches rubric wording) ----
    @Test
    @DisplayName("Registration failure: password does not meet complexity")
    void registrationFailBadPassword() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "password", "+2783986976");

        assertTrue(l.checkUserName());
        assertFalse(l.checkPasswordComplexity());
        assertTrue(l.checkCellPhoneNumber());

        String expected =
                "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expected, l.registerUser());
    }

    // ---- Cellphone failure branch (matches rubric wording) ----
    @Test
    @DisplayName("Registration failure: cellphone incorrectly formatted / no intl code")
    void registrationFailBadCell() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "0896653");

        assertTrue(l.checkUserName());
        assertTrue(l.checkPasswordComplexity());
        assertFalse(l.checkCellPhoneNumber());

        String expected =
                "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        assertEquals(expected, l.registerUser());
    }

    // ---- Login success (as if user typed correct creds into dialogs) ----
    @Test
    @DisplayName("Login success with correct credentials")
    void loginSuccess() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");

        boolean ok = l.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(ok);

        String expected = "Welcome Kyl, Smith it is great to see you again.";
        assertEquals(expected, l.returnLoginStatus(ok));
    }

    // ---- Login failure (wrong password) ----
    @Test
    @DisplayName("Login failure with incorrect password")
    void loginFailure() {
        Login l = new Login("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+2783986976");

        boolean ok = l.loginUser("kyl_1", "wrongPass");
        assertFalse(ok);

        String expected = "Username or password incorrect, please try again.";
        assertEquals(expected, l.returnLoginStatus(ok));
    }
    
}
