package controller.config.converter;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.SomeSuccessResponse;
@Component
public class AutoConfigResponseMapper {
    public SomeSuccessResponse success() {
        SomeSuccessResponse dto = new SomeSuccessResponse();
        dto.setSuccess(true);
        return dto;
    }
}



