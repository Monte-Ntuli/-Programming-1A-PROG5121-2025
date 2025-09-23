package za.co.monte.chat;

import java.util.regex.Pattern;

public class Login {
    // ===== Stored user details (captured during registration) =====
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhone;

    public Login() {}

    public Login(String firstName, String lastName, String username, String password, String cellPhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
    }

    // ----- Getters / setters -----
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getUsername()  { return username; }
    public String getPassword()  { return password; }
    public String getCellPhone() { return cellPhone; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }
    public void setUsername(String username)   { this.username = username; }
    public void setPassword(String password)   { this.password = password; }
    public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }

    // ==============================================================
    // =                REQUIREMENT METHODS                         =
    // ==============================================================

    /** Ensures username contains an underscore and is no more than 5 chars. */
    public boolean checkUserName() {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Password must be: at least 8 chars, contain a capital letter, a number,
     * and a special character.
     */
    public boolean checkPasswordComplexity() {
        if (password == null) return false;
        boolean longEnough = password.length() >= 8;
        boolean hasUpper   = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasDigit   = Pattern.compile("\\d").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[^A-Za-z0-9]").matcher(password).find();
        return longEnough && hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Cellphone must include international code and number.
     * For South Africa we accept +27 followed by 9 digits (e.g., +27831234567).
     *
     * Note on attribution:
     *   The regular-expression approach was assisted by an AI tool (ChatGPT).
     */
    public boolean checkCellPhoneNumber() {
        if (cellPhone == null) return false;
        // Strict SA format: +27 and exactly 9 digits after it.
        return Pattern.matches("^\\+27\\d{9}$", cellPhone);
        // (If your lecturer wants "international code + up to 10 digits", use: "^\\+\\d{1,3}\\d{1,10}$")
    }

    /**
     * Returns the registration messaging for the rubric:
     *  - If username incorrect  -> username guidance message
     *  - Else if password bad   -> password guidance message
     *  - Else if cell incorrect -> cellphone guidance message
     *  - Else                   -> success messages
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }

        // Success (strings match the table)
        return "Username successfully captured.\n" +
               "Password successfully captured.\n" +
               "Cell number successfully captured.";
    }

    /** Verifies that entered login equals the stored (registered) details. */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername != null && enteredPassword != null
                && enteredUsername.equals(this.username)
                && enteredPassword.equals(this.password);
    }

    /** Returns the authentication status message per rubric. */
    public String returnLoginStatus(boolean loggedIn) {
        if (loggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}