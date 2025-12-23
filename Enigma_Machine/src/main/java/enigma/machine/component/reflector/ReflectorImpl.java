package enigma.machine.component.reflector;

import java.util.Map;
import java.io.Serializable;

public class ReflectorImpl implements Reflector, Serializable {
    private final String reflectorId;
    private final Map<Integer, Integer> mapping;


    public ReflectorImpl(String reflectorId, Map<Integer, Integer> mapping) {
        this.reflectorId = reflectorId;
        this.mapping = mapping;
    }

    @Override
    public String getReflectorId() {
        return reflectorId;
    }

    @Override
    public int reflect(int inputIndex) {
        return mapping.get(inputIndex);
    }

}
