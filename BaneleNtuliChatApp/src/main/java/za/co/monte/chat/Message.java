package za.co.monte.chat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    public enum Action { SEND, DISCARD, STORE }

    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String messageHash;
    private final Action action;

    private static int totalMessagesSent = 0;

    public Message(String messageId, int messageNumber, String recipient,
                   String text, Action action) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.action = action;
        this.messageHash = createMessageHash();

        if (action == Action.SEND)
            totalMessagesSent++;
    }

    public boolean checkMessageID() {
        return messageId != null && messageId.matches("\\d{10}");
    }

    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("^\\+27\\d{9}$");
    }

    public String createMessageHash() {
        String prefix = messageId.substring(0, 2);

        String first = "";
        String last = "";

        if (text != null && !text.isBlank()) {
            String[] parts = text.trim().split("\\s+");
            first = parts[0];
            last = parts[parts.length - 1].replaceAll("[^A-Za-z0-9']", "");
        }

        return (prefix + ":" + messageNumber + first + last).toUpperCase();
    }

    public String sendMessage() {
        return switch (action) {
            case SEND    -> "Message successfully sent.";
            case DISCARD -> "Press 0 to delete message.";
            case STORE   -> "Message successfully stored.";
        };
    }

    public String printFullMessage() {
        return "MessageID: " + messageId +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + text;
    }

    public void storeMessage() {
        try {
            String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

            String json = String.format(
                    "{\"time\":\"%s\",\"id\":\"%s\",\"num\":%d,\"recipient\":\"%s\",\"hash\":\"%s\",\"text\":\"%s\",\"action\":\"%s\"}%n",
                    now, escape(messageId), messageNumber,
                    escape(recipient), escape(messageHash), escape(text), action);

            Files.write(Path.of("messages.json"), json.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (Exception e) {
            // silent for PoE
        }
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // ------ static helpers ------
    public static String generateId10() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++)
            sb.append((int) (Math.random() * 10));
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static String validateBodyForRubric(String body) {
        if (body == null) return "Message ready to send.";
        if (body.length() > 250)
            return "Message exceeds 250 characters by " + (body.length() - 250) + ", please reduce size.";
        return "Message ready to send.";
    }

    // ------ getters ------
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getText() { return text; }
    public String getMessageHashPublic() { return messageHash; }
    public Action getAction() { return action; }
}