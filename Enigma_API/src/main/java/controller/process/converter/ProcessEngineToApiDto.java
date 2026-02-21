package controller.process.converter;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.ProcessInput200Response;

@Component
public class ProcessEngineToApiDto {
    public ProcessInput200Response toDto(String engineOutput) {
        ProcessInput200Response response = new ProcessInput200Response();
        response.setOutput(engineOutput);
        return response;
    }
}

