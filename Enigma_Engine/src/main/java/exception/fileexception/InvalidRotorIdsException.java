package exception.fileexceoption;

public class InvalidRotorIdsException extends exception.fileexceoption.FileValidationException {
    public InvalidRotorIdsException() {
        super("Rotor IDs must be unique and sequential starting from 1 without gaps.");
    }
}
