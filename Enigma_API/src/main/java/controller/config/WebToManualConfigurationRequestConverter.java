package controller.config;

import dto.config.manual.ManualConfigurationModel;
import patmal.course.enigma.api.model.EnigmaManualConfigRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
// converter from openapi dto to service(engine) dto for manual configuration
public class WebToManualConfigurationRequestConverter {
    public ManualConfigurationModel convert(EnigmaManualConfigRequest request) {


        String line = request.getRotors().stream()
                .map(rotor -> String.valueOf(rotor.getRotorNumber()))
                .collect(Collectors.joining(","));

        String initialPositions = request.getRotors().stream()
                .map(rotor -> rotor.getRotorPosition())
                .collect(Collectors.joining());

        int reflectorId = romanToInt(request.getReflector());

        String plugboardInput = request.getPlugs().stream()
                .map(plug -> plug.getPlug1() + plug.getPlug2())
                .collect(Collectors.joining());

        return new ManualConfigurationModel(
                line,
                initialPositions,
                reflectorId,
                plugboardInput
        );
    }

    private int romanToInt(String roman) {
        return switch (roman) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            default -> throw new IllegalArgumentException("Invalid reflector ID");
        };
    }
}






