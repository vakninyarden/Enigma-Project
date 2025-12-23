package exception.inputexception;

public class InvalidEnigmaCharacterException extends InputValidationException {
    public InvalidEnigmaCharacterException(char invalidChar) {
        super("Invalid character in Enigma machine: '" + invalidChar + "'");
    }
}
