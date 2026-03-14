package enigma.machine.component.plugboard;

import java.util.Map;

public interface PlugBoard {

    char plugboardSwap(char c);

    Map<Character, Character> getPlugboardMap();

}
