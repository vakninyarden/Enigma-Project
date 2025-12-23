package exception.fileexception;

public class InvalidRotorIdsException extends exception.fileexception.FileValidationException {
    public InvalidRotorIdsException() {
        super("Rotor IDs must be unique and sequential starting from 1 without gaps.");
    }
}
