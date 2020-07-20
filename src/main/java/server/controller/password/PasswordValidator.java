package server.controller.password;

import exceptions.WeakPasswordException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private ArrayList<String> theWorstPasswords;
    private final String NUMERICAL = "[0-9]";
    private final String ALPHABETICAL = "[[a-z][A-Z]]";
    private boolean enabled;
    private final int MINIMUM_CHARS_NUMBER = 5;
    private static PasswordValidator validator = new PasswordValidator();

    private PasswordValidator()  {
        enabled = true;
        theWorstPasswords = new ArrayList<>();
        File file = new File("src/main/java/server/controller/password/badpass.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert scanner != null;
        while (scanner.hasNextLine())
            theWorstPasswords.add(scanner.nextLine());
    }

    private Matcher getMatcher(String regex, String text){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void validatePassword(String password) throws WeakPasswordException {
        if (enabled) {
            if (password.length() < MINIMUM_CHARS_NUMBER)
                throw new WeakPasswordException("Too short password");
            if (!(getMatcher(NUMERICAL, password)).find())
                throw new WeakPasswordException("The password should contain at least one digit");
            if (!(getMatcher(ALPHABETICAL, password)).find())
                throw new WeakPasswordException("The password should contain at least one alphabet");
            if (theWorstPasswords.contains(password))
                throw new WeakPasswordException("The password is easy to guess!");
        }

    }

    public static PasswordValidator getInstance(){
        return validator;
    }

}
