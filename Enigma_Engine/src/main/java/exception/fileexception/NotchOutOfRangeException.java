package exception.fileexceoption;

public class NotchOutOfRangeException extends exception.fileexceoption.FileValidationException {
    public NotchOutOfRangeException(int rotorId) {
        super("Notch position for rotor " + rotorId + " is out of valid range (must match rotor size).");
    }
}

