package exception.inputexception;

public class RotorNotFoundException extends InputValidationException{

    public RotorNotFoundException(String message) {
        super("Rotor not found in the repository: " + message);
    }
}
