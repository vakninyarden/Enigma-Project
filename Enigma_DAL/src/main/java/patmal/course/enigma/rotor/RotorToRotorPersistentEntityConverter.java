package patmal.course.enigma.rotor;

import enigma.machine.component.rotor.Rotor;

import java.util.List;

public class RotorToRotorPersistentEntityConverter {

    public static RotorEntity convert(Rotor rotor) {
        RotorEntity entity = new RotorEntity();
        entity.setRotorId(rotor.getRotorId());
        entity.setNotch(rotor.getNotchIndex());
        entity.setWiringRight(listToString(rotor.getRightMapping()));
        entity.setWiringLeft(listToString(rotor.getLeftMapping()));
        return entity;

    }

    private static String listToString(List<Character> chars) {
        StringBuilder sb = new StringBuilder(chars.size());
        for (Character c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }
}



