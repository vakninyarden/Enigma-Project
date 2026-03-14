package enigma.machine.component.rotor;

import java.util.List;

public interface Rotor {
    int getRotorId();

    int getNotchIndex();

    int mapping(int indexInRotor, Direction direction);

    boolean atNotch();

    void step();

    void reset();

    void setOriginalPosition(int originalPosition);

    List<Character> getRightMapping();

    List<Character> getLeftMapping();

    int getCurrentPosition();

    int getOriginalPosition();
}