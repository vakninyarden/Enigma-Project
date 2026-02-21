package controller.loader;
import dto.response.EnigmaMachineWeb;
import org.springframework.stereotype.Component;

@Component
public class EnigmaMachineToWebConverter {
    public EnigmaMachineWeb createSuccessfullResponse(String machineName) {
        return EnigmaMachineWeb.builder()
                .success(true)
                .machineName(machineName)
                .errorMessage(null)
                .build();
    }
    public EnigmaMachineWeb createFailedResponse(String errorMessage) {
        return EnigmaMachineWeb.builder()
                .success(false)
                .machineName(null)
                .errorMessage(errorMessage)
                .build();
    }
}
