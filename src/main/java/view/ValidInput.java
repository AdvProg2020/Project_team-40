package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ValidInput {
    INTEGER("\\d+"),
    PHONE_NUMBER("\\d+"),
    EMAIL_ADDRESS(".+@\\w+\\.\\w+"),
    NAME("^[a-zA-Z]+$"),
    DOUBLE("\\d+\\.{0,1}\\d*"),
    DATE("^\\d{2}\\/\\d{2}\\/\\d{2}\\s[0-2]\\d:[0-5]\\d:[0-5]\\d$"),
    DEFAULT(".+");

    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ValidInput(String commandPattern) {
        this.commandPattern = Pattern.compile(commandPattern);
    }
}
