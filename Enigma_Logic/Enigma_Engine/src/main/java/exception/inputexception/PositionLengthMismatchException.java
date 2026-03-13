package exception.inputexception;

public class PositionLengthMismatchException extends InputValidationException{
    public PositionLengthMismatchException(String message) {
        super("Position length mismatch: " + message);
    }
}
