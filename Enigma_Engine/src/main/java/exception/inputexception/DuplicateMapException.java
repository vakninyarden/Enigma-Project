package exception.inputexception;

public class DuplicateMapException extends InputValidationException{
    public DuplicateMapException(String message) {
        super("Duplicate mapping found: " + message);
    }
}
