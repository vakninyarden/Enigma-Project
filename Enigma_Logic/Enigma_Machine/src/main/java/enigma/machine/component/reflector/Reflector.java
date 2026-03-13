package enigma.machine.component.reflector;


import lombok.Getter;

import java.util.Map;

public interface Reflector {
    String getReflectorId();
    int reflect(int inputIndex);
    Map<Integer, Integer> getMapping();
}
