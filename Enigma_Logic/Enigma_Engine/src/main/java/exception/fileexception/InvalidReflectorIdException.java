package exception.fileexception;

public class InvalidReflectorIdException extends exception.fileexception.FileValidationException {
    public InvalidReflectorIdException(String reflectorId) {
        super("Reflector ID '" + reflectorId + "' is invalid. Allowed: I, II, III, IV, V.");
    }
}

