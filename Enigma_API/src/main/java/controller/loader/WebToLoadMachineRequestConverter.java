package controller.loader;
import dto.LoadMachineCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
public class WebToLoadMachineRequestConverter {

    public LoadMachineCommand convert(MultipartFile file) {
        try {
            return new LoadMachineCommand(file.getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read uploaded file");
        }
    }
}