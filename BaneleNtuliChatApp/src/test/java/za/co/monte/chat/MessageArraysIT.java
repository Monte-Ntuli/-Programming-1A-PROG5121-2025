package za.co.monte.chat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageArraysIT {

    private List<Message> history;
    private Message m1;
    private Message m2;
    private Message m3;

    public MessageArraysIT() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        history = new ArrayList<>();
        m1 = new Message("1111111111", 1, "+27730000001", "Short", Message.Action.SEND);
        m2 = new Message("2222222222", 2, "+27730000002", "This is the longest message", Message.Action.SEND);
        m3 = new Message("3333333333", 3, "+27730000002", "Stored message only", Message.Action.STORE);

        history.add(m1);
        history.add(m2);
        history.add(m3);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testDisplaySenderAndRecipients() {
        MessageArrays instance = new MessageArrays(history);
        String sender = "Banele Ntuli";

        String result = instance.displaySenderAndRecipients(sender);

        assertTrue(result.contains("Banele Ntuli"));
        assertTrue(result.contains("+27730000001"));
        assertTrue(result.contains("+27730000002"));
    }

    @Test
    public void testGetLongestSentMessage() {
        MessageArrays instance = new MessageArrays(history);
        String result = instance.getLongestSentMessage();
        assertEquals("This is the longest message", result);
    }

    @Test
    public void testSearchByMessageId_Found() {
        MessageArrays instance = new MessageArrays(history);
        String result = instance.searchByMessageId("2222222222");
        assertTrue(result.contains("+27730000002"));
        assertTrue(result.contains("This is the longest message"));
    }

    @Test
    public void testSearchByMessageId_NotFound() {
        MessageArrays instance = new MessageArrays(history);
        String result = instance.searchByMessageId("9999999999");
        assertEquals("Message ID not found.", result);
    }

    @Test
    public void testSearchMessagesForRecipient_Found() {
        MessageArrays instance = new MessageArrays(history);
        String result = instance.searchMessagesForRecipient("+27730000002");

        assertTrue(result.contains("This is the longest message"));
        // Stored-only message is Action.STORE, not counted as "sent" in some logic
        // but our implementation only filters sent in sentIndexes()
    }

    @Test
    public void testSearchMessagesForRecipient_NotFound() {
        MessageArrays instance = new MessageArrays(history);
        String result = instance.searchMessagesForRecipient("+27739999999");
        assertEquals("No messages for that recipient.", result);
    }

    @Test
    public void testDeleteByMessageHash() {
        MessageArrays instance = new MessageArrays(history);
        String hashToDelete = m1.getMessageHashPublic();

        boolean deleted = instance.deleteByMessageHash(history, hashToDelete);
        assertTrue(deleted);
        assertEquals(2, history.size());
        assertFalse(history.stream().anyMatch(m -> m.getMessageHashPublic().equals(hashToDelete)));
    }

    @Test
    public void testBuildSentMessagesReport() {
        MessageArrays instance = new MessageArrays(history);
        String report = instance.buildSentMessagesReport();

        assertTrue(report.contains("1111111111"));
        assertTrue(report.contains("2222222222"));
        assertTrue(report.contains("+27730000001"));
        assertTrue(report.contains("+27730000002"));
    }

    @Test
    public void testReadStoredFromJSON_NotNull() {
        String[] result = MessageArrays.readStoredFromJSON();
        assertNotNull(result);
        // We don't assert exact contents because file might not exist yet.
    }
}
