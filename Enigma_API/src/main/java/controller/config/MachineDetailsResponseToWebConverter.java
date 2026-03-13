package controller.config;

import dto.config.details.MachineSnapshot;
import dto.config.details.RotorSnapshot;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.*;

import java.math.BigDecimal;
import java.util.*;


@Component
public class MachineDetailsResponseToWebConverter {

    public GetCurrentMachineStatus200Response convert(
            MachineSnapshot snapshot,
            boolean verbose) {

        if (!verbose) {
            return convertCompact(snapshot);
        }

        return convertVerbose(snapshot);
    }


   public GetCurrentMachineStatus200ResponseOneOf1 convertCompact(
           MachineSnapshot snapshot) {

       return new GetCurrentMachineStatus200ResponseOneOf1()
               .totalRotors(snapshot.getTotalRotors())
               .totalReflectors(snapshot.getTotalReflectors())
               .totalProcessedMessages(snapshot.getTotalProcessedMessages())
               .originalCodeCompact(snapshot.getOriginalCodeCompact())
               .currentRotorsPositionCompact(snapshot.getCurrentCodeCompact());
   }

    public GetCurrentMachineStatus200ResponseOneOf convertVerbose(MachineSnapshot snapshot) {
        EnigmaCodeStructure original =
                buildCodeStructure(snapshot, true);

        EnigmaCodeStructure current =
                buildCodeStructure(snapshot, false);

        return new GetCurrentMachineStatus200ResponseOneOf()
                .totalRotors(snapshot.getTotalRotors())
                .totalReflectors(snapshot.getTotalReflectors())
                .totalProcessedMessages(snapshot.getTotalProcessedMessages())
                .originalCode(original)
                .currentRotorsPosition(current)
                .originalCodeCompact(snapshot.getOriginalCodeCompact())
                .currentRotorsPositionCompact(snapshot.getCurrentCodeCompact());

    }



     /* ===============================
       PRIVATE HELPERS
       =============================== */

    private EnigmaCodeStructure buildCodeStructure(
            MachineSnapshot snapshot,
            boolean useOriginalPosition) {

        List<RotorSnapshot> rotors = snapshot.getRotors() != null ? snapshot.getRotors() : Collections.emptyList();
        Map<Character,Character> plugs = snapshot.getPlugboard() != null ? snapshot.getPlugboard() : Collections.emptyMap();


        return new EnigmaCodeStructure()
                .rotors(buildRotors(rotors, useOriginalPosition))
                .reflector(snapshot.getReflectorId())
                .plugs(buildPlugs(plugs));
    }

    private List<RotorSelectionWithNotch> buildRotors(
            List<RotorSnapshot> rotors,
            boolean useOriginalPosition) {

        List<RotorSelectionWithNotch> result = new ArrayList<>();

        for (RotorSnapshot rotor : rotors) {

            int position = useOriginalPosition
                    ? rotor.getOriginalPosition()
                    : rotor.getCurrentPosition();

            int distance = calculateNotchDistance(rotor, position);

            Character letter = useOriginalPosition
                    ? rotor.getOriginalLetter()
                    : rotor.getCurrentLetter();
            result.add(
                    new RotorSelectionWithNotch()
                            .rotorNumber(rotor.getRotorId())
                            .rotorPosition(letter != null ? String.valueOf(letter) : null)
                            .notchDistance(BigDecimal.valueOf(distance))
            );
        }

        return result;
    }

    private int calculateNotchDistance(RotorSnapshot rotor, int position) {

        return (rotor.getNotchIndex()
                - position
                + rotor.getAlphabetSize())
                % rotor.getAlphabetSize();
    }


    private List<PlugConnection> buildPlugs(
            Map<Character, Character> plugMap) {

        List<PlugConnection> plugs = new ArrayList<>();
        Set<Character> visited = new HashSet<>();

        for (Map.Entry<Character, Character> entry : plugMap.entrySet()) {

            char a = entry.getKey();
            char b = entry.getValue();

            if (!visited.contains(a) && !visited.contains(b)) {

                plugs.add(
                        new PlugConnection()
                                .plug1(String.valueOf(a))
                                .plug2(String.valueOf(b))
                );

                visited.add(a);
                visited.add(b);
            }
        }

        return plugs;
    }

    }