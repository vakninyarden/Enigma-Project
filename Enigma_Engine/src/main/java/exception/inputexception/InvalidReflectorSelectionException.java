package exception.inputexception;

public class InvalidReflectorSelectionException extends InputValidationException{
    public InvalidReflectorSelectionException(String message) {
        super("Invalid reflector selection: " + message);
    }
}
