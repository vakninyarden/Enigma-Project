package controller.loader.converter;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.LoadMachineFromXml200Response;

@Component
public class LoaderResponseToDtoMapper {
    public LoadMachineFromXml200Response success() {
        LoadMachineFromXml200Response dto =
                new LoadMachineFromXml200Response();
        dto.setSuccess(true);
        return dto;
    }
}



