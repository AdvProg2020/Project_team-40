package exceptions;

public class TooMuchLoginAttemptsException extends Exception {
    public TooMuchLoginAttemptsException(String message) {
        super(message);
    }
}
