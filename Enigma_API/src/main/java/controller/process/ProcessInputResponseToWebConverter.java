package controller.process;

import dto.process.ProcessInputResult;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.ProcessInput200Response;

@Component
public class ProcessInputResponseToWebConverter {

    public ProcessInput200Response convert(ProcessInputResult result) {
        return new ProcessInput200Response()
                .output(result.getOutput())
                .currentRotorsPositionCompact(result.getCurrentRotorsPositionCompact());
    }
}