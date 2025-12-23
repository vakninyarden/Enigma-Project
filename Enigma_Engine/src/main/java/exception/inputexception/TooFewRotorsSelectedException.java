package exception.inputexception;

public class TooFewRotorsSelectedException extends InputValidationException{
    public TooFewRotorsSelectedException(String message) {
        super("Not enough rotors selected: " + message);
    }
}
