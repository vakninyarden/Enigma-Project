package controller.config;

import dto.DtoMachineSpecification;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.GetCurrentMachineStatus200Response;
import patmal.course.enigma.api.model.GetCurrentMachineStatus200ResponseOneOf;
import patmal.course.enigma.api.model.GetCurrentMachineStatus200ResponseOneOf1;


@Component
public class MachineDetailsResponseToWebConverter {
    public GetCurrentMachineStatus200ResponseOneOf1 convert(DtoMachineSpecification spec,String CurrentRotorsPositionCompact) {

        GetCurrentMachineStatus200ResponseOneOf1 response =
                new GetCurrentMachineStatus200ResponseOneOf1();


        response.setTotalRotors(spec.getNumOfRotors());
        response.setTotalReflectors(spec.getNumOfReflectors());
        response.setTotalProcessedMessages(spec.getNumOfMessages());
        response.setOriginalCodeCompact(spec.getOriginalCode());
        response.setCurrentRotorsPositionCompact(CurrentRotorsPositionCompact);

        //response.setOriginalCode(spec.getOriginalCode());

        // שים לב:
        // showMachineDetails מחזיר:
        // originalCodeString
        // currentCodeString

        return response;
    }
}