package enigma.machine.component.plugboard;

import java.util.Map;

public interface PlugBoard {

    public char plugboardSwap(char c);
    public Map<Character, Character> getPlugboardMap();

}
