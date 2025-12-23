package exception.fileexceoption;

public class FileDoesNotExistException extends FileValidationException {
    public FileDoesNotExistException(String filePath) {
        super("File does not exist or is not accessible: " + filePath);
    }
}
