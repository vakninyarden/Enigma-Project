package exception.fileexceoption;

public class ReflectorSelfMappingException extends exception.fileexceoption.FileValidationException {
    public ReflectorSelfMappingException(String letter, String reflectorId) {
        super("Reflector '" + reflectorId + "' contains illegal self-mapping: " + letter + " → " + letter);
    }
}
