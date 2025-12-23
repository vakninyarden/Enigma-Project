package enigma.machine.component.setting;

import enigma.machine.component.plugboard.PlugBoard;
import enigma.machine.component.rotor.Rotor;
import  enigma.machine.component.reflector.Reflector;

import java.io.Serializable;
import java.util.List;


public interface Setting {
    Reflector getReflector();
    List<RotorPosition> getActiveRotors();
    PlugBoard getPlugboard();

    public static class RotorPosition implements Serializable {

        public Rotor rotor;
        public int position;

        public RotorPosition(Rotor rotor, int position) {
            this.rotor = rotor;
            this.rotor.setOriginalPosition(position);
            this.position = position;
        }
        public Rotor getRotor() {
            return rotor;
        }

    }
}
