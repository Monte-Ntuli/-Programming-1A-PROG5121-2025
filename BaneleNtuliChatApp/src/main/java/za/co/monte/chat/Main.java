package za.co.monte.chat;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import za.co.monte.chat.Message.Action;

public class Main {

    public static void main(String[] args) {

        // -------------------- PART 1: REGISTRATION --------------------
        Login login;

        while (true) {
            String first = JOptionPane.showInputDialog("=== Registration ===\nEnter First name:");
            if (first == null) return;

            String last = JOptionPane.showInputDialog("Enter Last name:");
            if (last == null) return;

            String user = JOptionPane.showInputDialog("Create Username (must contain '_' and be <= 5 chars):");
            if (user == null) return;

            String pass = JOptionPane.showInputDialog("Create Password (>=8, include UPPERCASE, number, special):");
            if (pass == null) return;

            String phone = JOptionPane.showInputDialog("Enter Cellphone in format +27#########:");
            if (phone == null) return;

            login = new Login(first, last, user, pass, phone);
            String res = login.registerUser();
            JOptionPane.showMessageDialog(null, res);

            if (login.checkUserName() && login.checkPasswordComplexity() && login.checkCellPhoneNumber())
                break;

            JOptionPane.showMessageDialog(null, "Please try again.");
        }

        // -------------------- PART 1: LOGIN --------------------
        boolean ok = false;

        for (int i = 0; i < 3; i++) {
            String u = JOptionPane.showInputDialog("=== Login ===\nEnter Username:");
            if (u == null) return;

            String p = JOptionPane.showInputDialog("Enter Password:");
            if (p == null) return;

            ok = login.loginUser(u, p);
            JOptionPane.showMessageDialog(null, login.returnLoginStatus(ok));

            if (ok) break;
        }

        if (!ok) return;

        // -------------------- PART 2 + PART 3 --------------------
        List<Message> history = new ArrayList<>();
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        while (true) {
            String option = JOptionPane.showInputDialog("""
                    1) Send Messages
                    2) Message Tools (Part 3)
                    3) Show Recently Sent
                    4) Quit
                    """);

            if (option == null) return;

            switch (option.trim()) {
                case "1" -> sendMessages(history);
                case "2" -> messageTools(history, login);
                case "3" -> showRecentSent(history);
                case "4" -> { return; }
                default -> JOptionPane.showMessageDialog(null, "Choose 1–4.");
            }
        }
    }

    // ---------------------------------------------------------------
    // SEND MESSAGES
    // ---------------------------------------------------------------
    private static void sendMessages(List<Message> history) {
        int count = askInt("How many messages?", 1, 20);

        for (int i = 1; i <= count; i++) {
            String r = JOptionPane.showInputDialog("Enter recipient (+27#########):");
            if (r == null) break;

            String txt = JOptionPane.showInputDialog("Enter message (<= 250 chars):");
            if (txt == null) break;

            String val = Message.validateBodyForRubric(txt);
            JOptionPane.showMessageDialog(null, val);

            if (!val.startsWith("Message ready")) {
                txt = JOptionPane.showInputDialog("Re-enter message (<=250):");
            }

            String act = JOptionPane.showInputDialog("""
                    1) Send
                    2) Discard
                    3) Store for later
                    """);

            Action action = switch (act.trim()) {
                case "1" -> Action.SEND;
                case "2" -> Action.DISCARD;
                case "3" -> Action.STORE;
                default -> Action.DISCARD;
            };

            Message msg = new Message(Message.generateId10(), i, r, txt, action);
            JOptionPane.showMessageDialog(null, msg.sendMessage());

            if (action == Action.STORE)
                msg.storeMessage();

            history.add(msg);

            if (action == Action.SEND)
                JOptionPane.showMessageDialog(null, msg.printFullMessage());
        }

        JOptionPane.showMessageDialog(null,
                "Total sent: " + Message.returnTotalMessages());
    }

    // ---------------------------------------------------------------
    // PART 3 MESSAGE TOOLS
    // ---------------------------------------------------------------
    private static void messageTools(List<Message> history, Login login) {
        MessageArrays arr = new MessageArrays(history);

        while (true) {
            String opt = JOptionPane.showInputDialog("""
                    1) Display sender + recipients
                    2) Display longest sent message
                    3) Search by Message ID
                    4) Search messages for recipient
                    5) Delete by hash
                    6) Full sent messages report
                    7) Send a stored message
                    8) Back
                    """);

            if (opt == null || opt.equals("8")) return;

            switch (opt) {
                case "1" -> JOptionPane.showMessageDialog(null,
                        arr.displaySenderAndRecipients(login.getFirstName() + " " + login.getLastName()));

                case "2" -> JOptionPane.showMessageDialog(null,
                        arr.getLongestSentMessage());

                case "3" -> {
                    String id = JOptionPane.showInputDialog("Enter message ID:");
                    JOptionPane.showMessageDialog(null, arr.searchByMessageId(id));
                }

                case "4" -> {
                    String rec = JOptionPane.showInputDialog("Recipient number:");
                    JOptionPane.showMessageDialog(null, arr.searchMessagesForRecipient(rec));
                }

                case "5" -> {
                    String h = JOptionPane.showInputDialog("Enter hash to delete:");
                    boolean deleted = arr.deleteByMessageHash(history, h);
                    JOptionPane.showMessageDialog(null,
                            deleted ? "Deleted." : "Hash not found.");
                }

                case "6" -> JOptionPane.showMessageDialog(null,
                        arr.buildSentMessagesReport());

                case "7" -> sendStored(history);

                default -> JOptionPane.showMessageDialog(null, "Choose 1–8.");
            }
        }
    }

    // ---------------------------------------------------------------
    // SEND STORED MESSAGE
    // ---------------------------------------------------------------
    private static void sendStored(List<Message> history) {

        List<Message> stored = new ArrayList<>();
        for (Message m : history)
            if (m.getAction() == Action.STORE)
                stored.add(m);

        if (stored.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No stored messages.");
            return;
        }

        StringBuilder sb = new StringBuilder("Choose stored message:\n");
        for (int i = 0; i < stored.size(); i++)
            sb.append(i + 1).append(") ").append(stored.get(i).getText()).append("\n");

        String choice = JOptionPane.showInputDialog(sb.toString());
        if (choice == null) return;

        try {
            int idx = Integer.parseInt(choice) - 1;
            if (idx < 0 || idx >= stored.size()) {
                JOptionPane.showMessageDialog(null, "Invalid.");
                return;
            }

            Message old = stored.get(idx);

            Message sent = new Message(
                    Message.generateId10(),
                    history.size() + 1,
                    old.getRecipient(),
                    old.getText(),
                    Action.SEND);

            history.add(sent);

            JOptionPane.showMessageDialog(null,
                    "Stored message sent:\n" + sent.printFullMessage());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid.");
        }
    }

    // ---------------------------------------------------------------
    // SHOW RECENT SENT MESSAGES
    // ---------------------------------------------------------------
    private static void showRecentSent(List<Message> history) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (int i = history.size() - 1; i >= 0 && count < 5; i--) {
            Message m = history.get(i);
            if (m.getAction() == Action.SEND) {
                sb.append(m.printFullMessage()).append("\n\n");
                count++;
            }
        }

        JOptionPane.showMessageDialog(null,
                count == 0 ? "No sent messages." : sb.toString());
    }

    private static int askInt(String msg, int min, int max) {
        while (true) {
            String s = JOptionPane.showInputDialog(msg);
            try {
                int val = Integer.parseInt(s);
                if (val >= min && val <= max) return val;
            } catch (Exception ignored) {}
            JOptionPane.showMessageDialog(null, "Enter " + min + "–" + max);
        }
    }
}