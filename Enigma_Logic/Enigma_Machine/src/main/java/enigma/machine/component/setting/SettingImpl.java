package enigma.machine.component.setting;

import enigma.machine.component.plugboard.PlugBoard;
import enigma.machine.component.reflector.Reflector;

import java.util.List;
import java.io.Serializable;

public class SettingImpl implements Setting, Serializable {
    private Reflector reflector;
    private List<RotorPosition> activeRotors;
    private PlugBoard plugboard;

    public SettingImpl(Reflector reflector, List<RotorPosition> activeRotors, PlugBoard plugboard) {
        this.reflector = reflector;
        this.activeRotors = activeRotors;
        this.plugboard = plugboard;
    }

    @Override
    public Reflector getReflector() {
        return reflector;
    }

    @Override
    public List<RotorPosition> getActiveRotors() {
        return activeRotors;
    }

    @Override
    public PlugBoard getPlugboard() {
        return plugboard;
    }
}
