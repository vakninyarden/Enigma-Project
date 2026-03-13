package exception.fileexception;

public class RotorCountOutOfRangeException extends FileValidationException{
    public RotorCountOutOfRangeException(String message) {
        super("Rotor count out of range: " + message);
    }
}
