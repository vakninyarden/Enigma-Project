
package controller.config.converter;
import bte.component.jaxb.BTEEnigma;
import enigma.machine.component.machine.EnigmaMachineImpl;
import enigma.machine.component.rotor.RotorImpl;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MachineStatusMapper {

    public GetCurrentMachineStatus200Response toDto(EnigmaMachineImpl machine) {

        GetCurrentMachineStatus200Response response =
                new GetCurrentMachineStatus200Response();

        EnigmaCodeStructure structure = new EnigmaCodeStructure();

        // Rotors
        List<RotorSelectionWithNotch> rotorDtos = new ArrayList<>();
        for (RotorImpl rotor : machine.getSetting()..getActiveRotors()) {
            RotorSelectionWithNotch dto = new RotorSelectionWithNotch();
            dto.setId(rotor.getId());
            dto.setCurrentPosition(rotor.getCurrentPosition());
            dto.setNotchDistance(rotor.getDistanceToNotch());

            rotorDtos.add(dto);
        }

        structure.setRotors(rotorDtos);
        structure.setReflector(machine.getActiveReflector().getId());

        // Plugboard
        PlugBoardImpl plugBoard = machine.getPlugBoard();
        List<PlugConnection> plugDtos = new ArrayList<>();

        plugBoard.getConnections().forEach((a, b) -> {
            PlugConnection plug = new PlugConnection();
            plug.setFirstLetter(a);
            plug.setSecondLetter(b);
            plugDtos.add(plug);
        });

        structure.setPlugs(plugDtos);

        response.setConfiguration(structure);

        return response;
    }
}