package enigma.machine.component.rotor;

import java.util.List;

public interface Rotor {
    int getRotorId();
    public int getNotchIndex();
    int mapping(int indexInRotor, Direction direction);
    boolean atNotch();
    void step();
    void reset();
    public void setOriginalPosition(int originalPosition) ;
    public List<Character> getRightMapping() ;
    public int getCurrentPosition() ;
    public int getOriginalPosition() ;
}