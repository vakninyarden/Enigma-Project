package controller.config;

import dto.DtoMachineSpecification;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.GetCurrentMachineStatus200Response;
import patmal.course.enigma.api.model.GetCurrentMachineStatus200ResponseOneOf;


@Component
public class MachineDetailsResponseToWebConverter {
    public GetCurrentMachineStatus200ResponseOneOf convert(DtoMachineSpecification spec) {

        GetCurrentMachineStatus200ResponseOneOf response =
                new GetCurrentMachineStatus200ResponseOneOf();


        response.setTotalRotors(spec.getNumOfRotors());
        response.setTotalReflectors(spec.getNumOfReflectors());
        response.setTotalProcessedMessages(spec.getNumOfMessages());
        //response.setOriginalCode(spec.getOriginalCode());

        // שים לב:
        // showMachineDetails מחזיר:
        // originalCodeString
        // currentCodeString

        response.setOriginalCodeCompact(spec.getOriginalCode());
        response.setCurrentRotorsPositionCompact(spec.getCurrentCode());

        return response;
    }
}