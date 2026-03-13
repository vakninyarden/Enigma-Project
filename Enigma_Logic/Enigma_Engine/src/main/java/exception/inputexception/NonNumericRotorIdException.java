package exception.inputexception;

public class NonNumericRotorIdException extends  InputValidationException{
    public NonNumericRotorIdException(String message) {
        super("Rotor ID must be numeric." );
    }
}
