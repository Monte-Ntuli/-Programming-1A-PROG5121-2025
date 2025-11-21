package za.co.monte.chat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class MessageIT {

    public MessageIT() {
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
    public void testCheckMessageID_Valid() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello", Message.Action.SEND);
        assertTrue(instance.checkMessageID());
    }

    @Test
    public void testCheckMessageID_Invalid() {
        Message instance = new Message("12345", 1, "+27730000001", "Hello", Message.Action.SEND);
        assertFalse(instance.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello", Message.Action.SEND);
        assertTrue(instance.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        Message instance = new Message("1234567890", 1, "0730000001", "Hello", Message.Action.SEND);
        assertFalse(instance.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHash() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello world", Message.Action.SEND);
        String expResult = "12:1HELLOWORLD";
        String result = instance.createMessageHash();
        assertEquals(expResult, result);
    }

    @Test
    public void testSendMessage_Send() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello", Message.Action.SEND);
        assertEquals("Message successfully sent.", instance.sendMessage());
    }

    @Test
    public void testSendMessage_Discard() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello", Message.Action.DISCARD);
        assertEquals("Press 0 to delete message.", instance.sendMessage());
    }

    @Test
    public void testSendMessage_Store() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello", Message.Action.STORE);
        assertEquals("Message successfully stored.", instance.sendMessage());
    }

    @Test
    public void testPrintFullMessage() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello world", Message.Action.SEND);
        String hash = instance.getMessageHashPublic();
        String exp = "MessageID: 1234567890\n" +
                     "Message Hash: " + hash + "\n" +
                     "Recipient: +27730000001\n" +
                     "Message: Hello world";
        assertEquals(exp, instance.printFullMessage());
    }

    @Test
    public void testStoreMessage_CreatesFile() throws Exception {
        Path file = Path.of("messages.json");
        Files.deleteIfExists(file);

        Message instance = new Message("1234567890", 1, "+27730000001", "Test store", Message.Action.STORE);
        instance.storeMessage();

        assertTrue(Files.exists(file));
    }

    @Test
    public void testGenerateId10() {
        String id = Message.generateId10();
        assertEquals(10, id.length());
        assertTrue(id.matches("\\d{10}"));
    }

    @Test
    public void testReturnTotalMessages() {
        int before = Message.returnTotalMessages();
        new Message("9999999999", 99, "+27730000001", "Hi", Message.Action.SEND);
        int after = Message.returnTotalMessages();
        assertEquals(before + 1, after);
    }

    @Test
    public void testValidateBodyForRubric_Ready() {
        String result = Message.validateBodyForRubric("Short message");
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testValidateBodyForRubric_TooLong() {
        String longBody = "x".repeat(260);
        String result = Message.validateBodyForRubric(longBody);
        assertTrue(result.startsWith("Message exceeds 250 characters by "));
    }

    @Test
    public void testGetMessageId() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hi", Message.Action.SEND);
        assertEquals("1234567890", instance.getMessageId());
    }

    @Test
    public void testGetMessageNumber() {
        Message instance = new Message("1234567890", 5, "+27730000001", "Hi", Message.Action.SEND);
        assertEquals(5, instance.getMessageNumber());
    }

    @Test
    public void testGetRecipient() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hi", Message.Action.SEND);
        assertEquals("+27730000001", instance.getRecipient());
    }

    @Test
    public void testGetText() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello there", Message.Action.SEND);
        assertEquals("Hello there", instance.getText());
    }

    @Test
    public void testGetMessageHashPublic() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hello world", Message.Action.SEND);
        String expected = instance.createMessageHash();
        assertEquals(expected, instance.getMessageHashPublic());
    }

    @Test
    public void testGetAction() {
        Message instance = new Message("1234567890", 1, "+27730000001", "Hi", Message.Action.STORE);
        assertEquals(Message.Action.STORE, instance.getAction());
    }
}
