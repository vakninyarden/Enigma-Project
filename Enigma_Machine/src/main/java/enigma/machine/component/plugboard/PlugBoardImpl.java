package enigma.machine.component.plugboard;

import java.util.Map;

public class PlugBoardImpl implements PlugBoard {

    private Map<Character, Character> plugboardMapping;

    public PlugBoardImpl(Map<Character, Character> plugboardMapping) {
        this.plugboardMapping = plugboardMapping;
    }

    @Override
    public char plugboardSwap(char latter) {
        if(plugboardMapping.containsKey(latter)) {
            return plugboardMapping.get(latter);
        }
        return latter;
    }
}
