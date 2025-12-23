package exception.fileexceoption;

public class DuplicateRotorMappingException extends FileValidationException {
    public DuplicateRotorMappingException(int rotorId) {
        super("Rotor " + rotorId + " contains duplicate mappings in left/right columns.");
    }
}
