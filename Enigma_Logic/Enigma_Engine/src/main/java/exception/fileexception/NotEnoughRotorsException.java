package exception.fileexception;

public class NotEnoughRotorsException extends exception.fileexception.FileValidationException {
    public NotEnoughRotorsException(int actualCount) {
        super("At least 3 rotors are required. Provided: " + actualCount);
    }
}
