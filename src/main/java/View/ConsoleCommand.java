package View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ConsoleCommand {
    INTEGER("\\d+"),
    PHONE_NUMBER("\\d+"),
    EMAIL_ADDRESS(".+@\\w+\\.\\w+"),
    NAME("(\\w|\\s)+"),
    DOUBLE("\\d+\\.{0,1}\\d*");

    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ConsoleCommand(String commandPattern) {
        this.commandPattern = Pattern.compile(commandPattern);
    }
}
