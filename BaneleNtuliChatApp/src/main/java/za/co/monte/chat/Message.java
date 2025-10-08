package za.co.monte.chat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Message entity + utilities for Part 2.
 * - Generates a 10-digit message ID
 * - Builds the required MESSAGE HASH (first two digits of ID + ":" + msgNo + first & last words, UPPERCASE)
 * - Tracks total messages SENT (counter)
 * - Can store a message as JSON (simple line-delimited JSON)
 */
public class Message {

    public enum Action { SEND, DISCARD, STORE }

    private final String messageId;    // 10-digit random string
    private final int messageNumber;   // i = 1..N in the run
    private final String recipient;    // cell number (+27#########)
    private final String text;         // <= 250 chars
    private final String messageHash;  // per spec (uppercase)
    private final Action action;       // SEND / DISCARD / STORE

    private static int totalMessagesSent = 0;  // accumulated when SEND chosen

    // ---------- ctor ----------
    public Message(String messageId, int messageNumber, String recipient, String text, Action action) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.action = action;
        this.messageHash = createMessageHash(); // compute on build
        // bump counter if sent
        if (action == Action.SEND) totalMessagesSent++;
    }

    // ---------- required checks ----------
    /** Message ID must be <= 10 characters (we generate exactly 10 digits). */
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    /**
     * Recipient must include international code and be a valid SA cell:
     * +27 followed by exactly 9 digits. Example: +27718693002
     * (To allow "any country code with up to 10 digits", change to: "^\\+\\d{1,10}$")
     */
    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("^\\+27\\d{9}$");
    }

    // ---------- string manipulation tasks ----------
    /** Builds the MESSAGE HASH: first two digits of ID + ":" + messageNumber + first & last words (UPPERCASE). */
    public String createMessageHash() {
        String idPrefix = (messageId != null && messageId.length() >= 2) ? messageId.substring(0, 2) : "00";
        String first = "", last = "";
        if (text != null && !text.isBlank()) {
            String[] parts = text.trim().split("\\s+");
            first = parts[0];
            last = parts[parts.length - 1].replaceAll("[^A-Za-z0-9']", ""); // strip trailing punctuation
        }
        return (idPrefix + ":" + messageNumber + first + last).toUpperCase();
    }

    /** Allows outer code to branch on action and returns rubric strings. */
    public String sendMessage() {
        return switch (action) {
            case SEND -> "Message successfully sent.";
            case DISCARD -> "Press 0 to delete message.";
            case STORE -> "Message successfully stored.";
        };
    }

    /** Returns a single-line printable record for lists. */
    public String printMessages() {
        return "ID=" + messageId + ", No=" + messageNumber + ", To=" + recipient + ", Hash=" + messageHash
                + ", Text=\"" + text + "\" , Action=" + action;
    }

    /** Returns the running total of SENT messages. */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    /** Persist a message as JSON (newline-delimited JSON in messages.json). */
    public void storeMessage() {
        try {
            String iso = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String json = String.format(
                    "{\"timestamp\":\"%s\",\"messageId\":\"%s\",\"messageNumber\":%d,\"recipient\":\"%s\",\"hash\":\"%s\",\"text\":\"%s\",\"action\":\"%s\"}%n",
                    iso, escape(messageId), messageNumber, escape(recipient), escape(messageHash), escape(text), action);
            Path file = Path.of("messages.json");
            Files.write(file, json.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
        } catch (Exception e) {
            // keep it quiet for PoE; in production we'd log it
        }
    }

    // simple JSON escaper
    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // ---------- getters ----------
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getText() { return text; }
    public String getMessageHashPublic() { return messageHash; }
    public Action getAction() { return action; }

    // utility: generate a random 10-digit numeric string
    public static String generateId10() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) sb.append((int)(Math.random() * 10));
        return sb.toString();
    }

    // static helper: length check against 250 and returns rubric messages
    public static String validateBodyForRubric(String body) {
        int len = (body == null) ? 0 : body.length();
        if (len > 250) {
            return "Message exceeds 250 characters by " + (len - 250) + ", please reduce size.";
        }
        return "Message ready to send.";
    }
}
