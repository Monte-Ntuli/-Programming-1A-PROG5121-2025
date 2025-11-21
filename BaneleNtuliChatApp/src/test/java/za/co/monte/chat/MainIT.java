package za.co.monte.chat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainIT {

    public MainIT() {
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

    /**
     * We don't actually call Main.main() here because it opens GUI dialogs.
     * This test just checks that the class can be instantiated.
     */
    @Test
    public void testMainClassLoads() {
        Main m = new Main(); // default constructor
        assertNotNull(m);
    }
}
