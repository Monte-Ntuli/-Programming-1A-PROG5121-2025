package za.co.monte.chat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MessageArrays {

    private final String[] ids;
    private final String[] hashes;
    private final String[] recipients;
    private final String[] texts;
    private final Message.Action[] actions;

    public MessageArrays(List<Message> history) {
        int n = history.size();

        ids = new String[n];
        hashes = new String[n];
        recipients = new String[n];
        texts = new String[n];
        actions = new Message.Action[n];

        for (int i = 0; i < n; i++) {
            Message m = history.get(i);
            ids[i] = m.getMessageId();
            hashes[i] = m.getMessageHashPublic();
            recipients[i] = m.getRecipient();
            texts[i] = m.getText();
            actions[i] = m.getAction();
        }
    }

    private int[] sentIndexes() {
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i < actions.length; i++)
            if (actions[i] == Message.Action.SEND)
                idx.add(i);
        return idx.stream().mapToInt(Integer::intValue).toArray();
    }

    // a.
    public String displaySenderAndRecipients(String sender) {
        int[] sent = sentIndexes();
        if (sent.length == 0) return "No sent messages.";

        StringBuilder sb = new StringBuilder();
        for (int i : sent) {
            sb.append("Sender: ").append(sender)
              .append(" → Recipient: ").append(recipients[i]).append("\n");
        }
        return sb.toString();
    }

    // b.
    public String getLongestSentMessage() {
        int[] sent = sentIndexes();
        String longest = "";
        for (int i : sent)
            if (texts[i] != null && texts[i].length() > longest.length())
                longest = texts[i];
        return longest.isEmpty() ? "No sent messages." : longest;
    }

    // c.
    public String searchByMessageId(String searchId) {
        for (int i = 0; i < ids.length; i++)
            if (ids[i] != null && ids[i].equals(searchId))
                return "Recipient: " + recipients[i] + "\nMessage: " + texts[i];
        return "Message ID not found.";
    }

    // d.
    public String searchMessagesForRecipient(String r) {
        int[] sent = sentIndexes();
        StringBuilder sb = new StringBuilder();
        for (int i : sent)
            if (recipients[i] != null && recipients[i].equals(r))
                sb.append("ID: ").append(ids[i]).append(" → ").append(texts[i]).append("\n");
        return sb.length() == 0 ? "No messages for that recipient." : sb.toString();
    }

    // e.
    public boolean deleteByMessageHash(List<Message> history, String hash) {
        return history.removeIf(m -> m.getMessageHashPublic().equals(hash));
    }

    // f.
    public String buildSentMessagesReport() {
        int[] sent = sentIndexes();
        if (sent.length == 0) return "No sent messages.";

        StringBuilder sb = new StringBuilder("""
                MessageID | Hash | Recipient | Message
                ---------------------------------------
                """);

        for (int i : sent)
            sb.append(ids[i]).append(" | ")
              .append(hashes[i]).append(" | ")
              .append(recipients[i]).append(" | ")
              .append(texts[i]).append("\n");

        return sb.toString();
    }

    // g.
    public static String[] readStoredFromJSON() {
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                int start = line.indexOf("\"text\":\"");
                if (start != -1) {
                    start += 8;
                    int end = line.indexOf("\"", start);
                    if (end > start)
                        list.add(line.substring(start, end));
                }
            }
        } catch (Exception ignored) {}

        return list.toArray(new String[0]);
    }
}