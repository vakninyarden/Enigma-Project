package controller.loader;

import dto.load.LoadMachineResult;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.LoadMachineFromXml200Response;

@Component
public class LoadMachineResponseToWebConverter {

    public LoadMachineFromXml200Response convert(LoadMachineResult result) {

        return new LoadMachineFromXml200Response()
                .success(true)
                .name(result.getMachineName())
                .error(null);
    }
}