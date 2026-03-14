package enigma.machine.component.reflector;


import java.io.Serializable;
import java.util.Map;

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

    public Map<Integer, Integer> getMapping() {
        return mapping;
    }
}

