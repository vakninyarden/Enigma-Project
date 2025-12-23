package exception.fileexceoption;

public class InvalidReflectorIdException extends exception.fileexceoption.FileValidationException {
    public InvalidReflectorIdException(String reflectorId) {
        super("Reflector ID '" + reflectorId + "' is invalid. Allowed: I, II, III, IV, V.");
    }
}

