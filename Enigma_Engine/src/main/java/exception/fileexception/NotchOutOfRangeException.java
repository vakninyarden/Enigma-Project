package exception.fileexception;

public class NotchOutOfRangeException extends exception.fileexception.FileValidationException {
    public NotchOutOfRangeException(int rotorId) {
        super("Notch position for rotor " + rotorId + " is out of valid range (must match rotor size).");
    }
}

