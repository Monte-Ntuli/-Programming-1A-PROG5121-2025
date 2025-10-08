package za.co.monte.chat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import za.co.monte.chat.Message.Action;

public class MessageTest {

    @Test
    void messageBodyUnderLimit_success() {
        String body = "Hi Mike, can you join us for dinner tonight";
        assertEquals("Message ready to send.", Message.validateBodyForRubric(body));
    }

    @Test
    void messageBodyOverLimit_failure() {
        String big = "x".repeat(260);
        String msg = Message.validateBodyForRubric(big);
        assertTrue(msg.startsWith("Message exceeds 250 characters by 10"));
    }

    @Test
    void recipientFormatted_success() {
        Message m = new Message("0011223344", 1, "+27718693002",
                "Hi Mike, can you join us for dinner tonight", Action.SEND);
        assertTrue(m.checkRecipientCell());
    }

    @Test
    void recipientFormatted_failure() {
        Message m = new Message("0011223344", 2, "08575975889",
                "Hi Keegan, did you receive the payment?", Action.DISCARD);
        assertFalse(m.checkRecipientCell());
    }

    @Test
    void messageIdIsCreatedAndValid() {
        String id = Message.generateId10();
        Message m = new Message(id, 1, "+27831234567", "Hello there", Action.SEND);
        assertTrue(m.checkMessageID());
        assertEquals(10, id.length());
    }

    @Test
    void messageHashIsCorrectForCase1() {
        // deterministic example
        Message m = new Message("0012345678", 1, "+27831234567",
                "Hi Mike, can you join us for dinner tonight", Action.SEND);
        assertEquals("00:1HITONIGHT", m.createMessageHash()); // first word "Hi", last word "tonight"
    }

    @Test
    void actionMessages() {
        Message a = new Message("1111111111", 1, "+27831234567", "A", Action.SEND);
        Message b = new Message("2222222222", 2, "+27831234567", "B", Action.DISCARD);
        Message c = new Message("3333333333", 3, "+27831234567", "C", Action.STORE);

        assertEquals("Message successfully sent.", a.sendMessage());
        assertEquals("Press 0 to delete message.", b.sendMessage());
        assertEquals("Message successfully stored.", c.sendMessage());
    }
}