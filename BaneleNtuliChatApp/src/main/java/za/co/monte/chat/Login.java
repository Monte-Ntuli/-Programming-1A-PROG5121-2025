package za.co.monte.chat;

import java.util.regex.Pattern;

public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhone;

    public Login() {}

    public Login(String firstName, String lastName, String username,
                 String password, String cellPhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
    }

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

    /** Username rule: must contain "_" and be <= 5 chars. */
    public boolean checkUserName() {
        return username != null &&
                username.contains("_") &&
                username.length() <= 5;
    }

    /** Password complexity: >=8, uppercase, digit, special character. */
    public boolean checkPasswordComplexity() {
        if (password == null) return false;

        boolean longEnough = password.length() >= 8;
        boolean hasUpper   = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasDigit   = Pattern.compile("\\d").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[^A-Za-z0-9]").matcher(password).find();

        return longEnough && hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Phone number validation (+27XXXXXXXXX).
     * Regex was created with assistance from ChatGPT (OpenAI, 2025).
     */
    public boolean checkCellPhoneNumber() {
        return cellPhone != null && Pattern.matches("^\\+27\\d{9}$", cellPhone);
    }

    /** Registration feedback. */
    public String registerUser() {
        if (!checkUserName())
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";

        if (!checkPasswordComplexity())
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        if (!checkCellPhoneNumber())
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";

        return "Username successfully captured.\n" +
               "Password successfully captured.\n" +
               "Cell number successfully captured.";
    }

    /** Check credentials. */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername != null && enteredPassword != null
                && enteredUsername.equals(username)
                && enteredPassword.equals(password);
    }

    /** Login status messaging. */
    public String returnLoginStatus(boolean loggedIn) {
        if (loggedIn)
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        return "Username or password incorrect, please try again.";
    }
}