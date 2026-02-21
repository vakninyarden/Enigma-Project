package controller.process.converter;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.ProcessInputRequest;

@Component

public class ProcessApiDtoToEngine {
    public String toEngineInput(ProcessInputRequest request) {
        return request.getInput();
    }
}




