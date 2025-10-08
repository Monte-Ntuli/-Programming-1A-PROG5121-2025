package za.co.monte.chat;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import za.co.monte.chat.Message.Action;

public class Main {

    public static void main(String[] args) {
        // -------------------- PART 1: SIGN-UP VIA JOptionPane --------------------
        Login login = null;

        while (true) {
            String first = JOptionPane.showInputDialog("=== Registration ===\nEnter First name:");
            if (first == null) return; // user cancelled
            String last = JOptionPane.showInputDialog("Enter Last name:");
            if (last == null) return;

            String user = JOptionPane.showInputDialog("Create Username (must contain '_' and be <= 5 chars):");
            if (user == null) return;

            String pass = JOptionPane.showInputDialog("Create Password (>=8, include UPPERCASE, number, special):");
            if (pass == null) return;

            String phone = JOptionPane.showInputDialog("Enter Cellphone in international format (e.g. +27#########):");
            if (phone == null) return;

            login = new Login(first.trim(), last.trim(), user.trim(), pass, phone.trim());

            // use the same messages the rubric expects
            String regMsg = login.registerUser();
            JOptionPane.showMessageDialog(null, regMsg);

            // break only when ALL checks pass
            if (login.checkUserName() && login.checkPasswordComplexity() && login.checkCellPhoneNumber()) {
                break; // successful registration
            } else {
                // loop to re-enter until valid (as per rubric’s expectation of correct formatting)
                JOptionPane.showMessageDialog(null, "Please fix the issues above and try registration again.");
            }
        }

        // -------------------- PART 1: LOGIN VIA JOptionPane ----------------------
        boolean ok = false;
        int attempts = 0;
        while (attempts < 3) {
            String u = JOptionPane.showInputDialog("=== Login ===\nEnter username:");
            if (u == null) return;
            String p = JOptionPane.showInputDialog("Enter password:");
            if (p == null) return;

            ok = login.loginUser(u.trim(), p);
            JOptionPane.showMessageDialog(null, login.returnLoginStatus(ok));
            if (ok) break;
            attempts++;
        }
        if (!ok) return; // must be logged in to proceed

        // -------------------- PART 2: MENU + MESSAGING --------------------------
        List<Message> history = new ArrayList<>();
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        while (true) {
            String choice = JOptionPane.showInputDialog(
                    """
                    Welcome to QuickChat.
                    Choose an option:
                    1) Send Messages
                    2) Show recently sent messages (Coming Soon)
                    3) Quit
                    """);
            if (choice == null) break;

            switch (choice.trim()) {
                case "1" -> {
                    int howMany = askIntBounded("How many messages would you like to enter?", 1, 20);
                    for (int i = 1; i <= howMany; i++) {
                        String recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27718693002):");
                        if (recipient == null) break;

                        String body = JOptionPane.showInputDialog("Enter message text (<= 250 characters):");
                        if (body == null) break;

                        String lenMsg = Message.validateBodyForRubric(body);
                        JOptionPane.showMessageDialog(null, lenMsg);
                        if (!lenMsg.startsWith("Message ready")) {
                            body = JOptionPane.showInputDialog("Re-enter message (<= 250 characters):");
                            if (body == null) break;
                        }

                        String actionStr = JOptionPane.showInputDialog(
                                """
                                Choose what to do with this message:
                                1) Send Message
                                2) Disregard Message
                                3) Store Message to send later
                                """);
                        if (actionStr == null) break;
                        Action action = switch (actionStr.trim()) {
                            case "1" -> Action.SEND;
                            case "2" -> Action.DISCARD;
                            case "3" -> Action.STORE;
                            default -> Action.DISCARD;
                        };

                        String id = Message.generateId10();
                        Message msg = new Message(id, i, recipient.trim(), body, action);

                        if (!msg.checkRecipientCell()) {
                            JOptionPane.showMessageDialog(null,
                                    "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        }

                        JOptionPane.showMessageDialog(null, msg.sendMessage());
                        if (action == Action.STORE) msg.storeMessage();
                        history.add(msg);

                        if (action == Action.SEND) {
                            JOptionPane.showMessageDialog(null,
                                    "MessageID: " + msg.getMessageId() + "\n" +
                                    "Message Hash: " + msg.getMessageHashPublic() + "\n" +
                                    "Recipient: " + msg.getRecipient() + "\n" +
                                    "Message: " + msg.getText());
                        }
                    }

                    JOptionPane.showMessageDialog(null,
                            "Total number of messages sent: " + Message.returnTotalMessages());
                }
                case "2" -> JOptionPane.showMessageDialog(null, "Coming Soon.");
                case "3" -> { return; }
                default -> JOptionPane.showMessageDialog(null, "Please choose 1, 2 or 3.");
            }
        }
    }

    // helper to ensure valid numeric input
    private static int askIntBounded(String prompt, int min, int max) {
        while (true) {
            String s = JOptionPane.showInputDialog(prompt + " (" + min + "-" + max + ")");
            if (s == null) return min;
            try {
                int n = Integer.parseInt(s.trim());
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException ignored) {}
            JOptionPane.showMessageDialog(null, "Please enter a valid number between " + min + " and " + max + ".");
        }
    }
}
