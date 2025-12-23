package exception.inputexception;

public class DuplicateRotorException extends InputValidationException {
    public DuplicateRotorException(String message) {
        super("Duplicate rotor found: " + message);
    }
}
