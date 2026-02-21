
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManualConfigMapper {

    public String buildRotorsString(List<RotorSelection> rotors) {
        return rotors.stream()
                .map(r -> String.valueOf(r.getId()))
                .collect(Collectors.joining(","));
    }

    public String buildWindowsString(List<RotorSelection> rotors) {
        return rotors.stream()
                .map(RotorSelection::getCurrentPosition)
                .collect(Collectors.joining());
    }

    public int extractReflectorId(EnigmaCodeStructureManual structure) {
        return structure.getReflector();
    }

    public String buildPlugsString(List<PlugConnection> plugs) {
        if (plugs == null || plugs.isEmpty()) {
            return "";
        }

        return plugs.stream()
                .map(p -> p.getFirstLetter() + "" + p.getSecondLetter())
                .collect(Collectors.joining("-"));
    }
}