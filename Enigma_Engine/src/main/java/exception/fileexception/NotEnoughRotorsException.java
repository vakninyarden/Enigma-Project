package exception.fileexceoption;

public class NotEnoughRotorsException extends exception.fileexceoption.FileValidationException {
    public NotEnoughRotorsException(int actualCount) {
        super("At least 3 rotors are required. Provided: " + actualCount);
    }
}
