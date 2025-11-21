package za.co.monte.chat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginIT {

    public LoginIT() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetFirstName() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Pass@123", "+27731234567");
        String result = instance.getFirstName();
        assertEquals("Simphiwe", result);
    }

    @Test
    public void testGetLastName() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Pass@123", "+27731234567");
        String result = instance.getLastName();
        assertEquals("Ntuli", result);
    }

    @Test
    public void testGetUsername() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Pass@123", "+27731234567");
        String result = instance.getUsername();
        assertEquals("sim_1", result);
    }

    @Test
    public void testGetPassword() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Pass@123", "+27731234567");
        String result = instance.getPassword();
        assertEquals("Pass@123", result);
    }

    @Test
    public void testGetCellPhone() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Pass@123", "+27731234567");
        String result = instance.getCellPhone();
        assertEquals("+27731234567", result);
    }

    @Test
    public void testSetFirstName() {
        Login instance = new Login();
        instance.setFirstName("Thabi");
        assertEquals("Thabi", instance.getFirstName());
    }

    @Test
    public void testSetLastName() {
        Login instance = new Login();
        instance.setLastName("Moyo");
        assertEquals("Moyo", instance.getLastName());
    }

    @Test
    public void testSetUsername() {
        Login instance = new Login();
        instance.setUsername("tha_1");
        assertEquals("tha_1", instance.getUsername());
    }

    @Test
    public void testSetPassword() {
        Login instance = new Login();
        instance.setPassword("Thabi@10");
        assertEquals("Thabi@10", instance.getPassword());
    }

    @Test
    public void testSetCellPhone() {
        Login instance = new Login();
        instance.setCellPhone("+27730000000");
        assertEquals("+27730000000", instance.getCellPhone());
    }

    @Test
    public void testCheckUserName_Valid() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Pass@123", "+27731234567");
        assertTrue(instance.checkUserName());
    }

    @Test
    public void testCheckUserName_Invalid() {
        Login instance = new Login("Banele", "Ntuli", "invalidName", "Pass@123", "+27731234567");
        assertFalse(instance.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Thabi@10", "+27731234567");
        assertTrue(instance.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        // no uppercase and no special character
        Login instance = new Login("Banele", "Ntuli", "sim_1", "thabi123", "+27731234567");
        assertFalse(instance.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumber_Valid() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Pass@123", "+27731234567");
        assertTrue(instance.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        // no +27 code
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Pass@123", "0731234567");
        assertFalse(instance.checkCellPhoneNumber());
    }

    @Test
    public void testRegisterUser_Success() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Thabi@10", "+27731234567");
        String expResult =
                "Username successfully captured.\n" +
                "Password successfully captured.\n" +
                "Cell number successfully captured.";
        String result = instance.registerUser();
        assertEquals(expResult, result);
    }

    @Test
    public void testRegisterUser_InvalidUsername() {
        Login instance = new Login("Banele", "Ntuli", "longname", "Thabi@10", "+27731234567");
        String result = instance.registerUser();
        assertTrue(result.startsWith("Username is not correctly formatted"));
    }

    @Test
    public void testLoginUser_Valid() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Thabi@10", "+27731234567");
        assertTrue(instance.loginUser("sim_1", "Thabi@10"));
    }

    @Test
    public void testLoginUser_Invalid() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Thabi@10", "+27731234567");
        assertFalse(instance.loginUser("wrong", "wrong"));
    }

    @Test
    public void testReturnLoginStatus_LoggedInTrue() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Thabi@10", "+27731234567");
        String result = instance.returnLoginStatus(true);
        assertEquals("Welcome Banele, Ntuli it is great to see you again.", result);
    }

    @Test
    public void testReturnLoginStatus_LoggedInFalse() {
        Login instance = new Login("Banele", "Ntuli", "sim_1", "Thabi@10", "+27731234567");
        String result = instance.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", result);
    }
}
