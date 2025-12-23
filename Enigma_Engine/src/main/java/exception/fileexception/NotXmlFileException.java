package exception.fileexception;

public class NotXmlFileException extends exception.fileexception.FileValidationException {
    public NotXmlFileException(String filePath) {
        super("The provided file is not an XML file: " + filePath);
    }
}
