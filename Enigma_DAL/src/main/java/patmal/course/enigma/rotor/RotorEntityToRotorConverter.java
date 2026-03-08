package patmal.course.enigma.rotor;

import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.rotor.RotorImpl;

import java.util.ArrayList;
import java.util.List;

public class RotorEntityToRotorConverter {
    public static Rotor convert(RotorEntity rotorEntity) {

        List<Character> rightMapping = stringToCharList(rotorEntity.getWiringRight());
        List<Character> leftMapping = stringToCharList(rotorEntity.getWiringLeft());

        return new RotorImpl(
                rotorEntity.getRotorId(),
                rotorEntity.getNotch(),
                rightMapping,
                leftMapping
        );

    }

    private static List<Character> stringToCharList(String wiring) {

        List<Character> list = new ArrayList<>();

        for (char c : wiring.toCharArray()) {
            list.add(c);
        }

        return list;
    }
}


