package enigma.machine.component.setting;

import enigma.machine.component.reflector.Reflector;

import java.util.List;
import java.io.Serializable;

public class SettingImpl implements Setting, Serializable {
    private Reflector reflector;
    private List<RotorPosition> activeRotors;

    public SettingImpl(Reflector reflector, List<RotorPosition> activeRotors) {
        this.reflector = reflector;
        this.activeRotors = activeRotors;
    }

    @Override
    public Reflector getReflector() {
        return reflector;
    }

    @Override
    public List<RotorPosition> getActiveRotors() {
        return activeRotors;
    }
}
