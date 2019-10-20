package com.example.androidgrouptask;
import java.util.regex.*;
public class registrationValidation {

    // registration inputs to be written to SQL lite database
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public registrationValidation(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    // validation of firstName input
    public boolean nameValidator(String Name) {
        if (Name.length() < 1) {
            return false;
        }
        if (Pattern.matches("[a-zA-Z]", Name)) { // regex method to ensure its only letters
            return true;
        }
        else {
            return false;
        }
    }
    // RFC822 valid regex statement
    public static final Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // email Validator
    public boolean emailValidator(String emailAddress) {
        Matcher matcher = emailRegex.matcher(emailAddress);
        if(matcher.find() == true) {
            return true;
        }
        else return false;
    }
    // password Regex
    public static final Pattern passwordRegex = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    // validate password
    public boolean validatePassword(String checkPassword) {
        Matcher matcher = passwordRegex.matcher(checkPassword);
        if(matcher.find() == true) {
            return true;
        }
        else return false;
    }
}