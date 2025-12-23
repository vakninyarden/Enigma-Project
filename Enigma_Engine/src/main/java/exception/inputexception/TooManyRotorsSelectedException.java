package exception.inputexception;

public class TooManyRotorsSelectedException extends  InputValidationException{
    public TooManyRotorsSelectedException(String message) {
        super("Too many rotors selected " + message);
    }
}
