package exception.inputexception;

public class SelfMappingException extends InputValidationException{
    public SelfMappingException(String message) {
        super("self mapping error: " + message);
    }
}
