package exception.fileexception;

public class ReflectorSelfMappingException extends exception.fileexception.FileValidationException {
    public ReflectorSelfMappingException(String letter, String reflectorId) {
        super("Reflector '" + reflectorId + "' contains illegal self-mapping: " + letter + " → " + letter);
    }
}
