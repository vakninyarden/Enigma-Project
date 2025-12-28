package enigma.machine.component.machine;

import enigma.machine.component.keyboard.KeyBoard;
import enigma.machine.component.rotor.Direction;
import enigma.machine.component.setting.Setting;
import enigma.machine.component.rotor.Rotor;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.List;


public class EnigmaMachineImpl implements EnigmaMachine, Serializable {

    private final KeyBoard keyboard;
    private Setting setting;

    public Setting getSetting() {
        return setting;
    }

    public EnigmaMachineImpl(KeyBoard keyboard, Setting setting) {
        this.keyboard = keyboard;
        this.setting = setting;
    }

    @Override
    public void resetMachine() {
        for (Setting.RotorPosition rotors : setting.getActiveRotors()) {
            rotors.rotor.reset();
        }
    }

    @Override
    public char processLatter(char latter) {
        char latterAfterPlugboard = setting.getPlugboard().plugboardSwap(latter);
        int intermediate = keyboard.processCharacter(latterAfterPlugboard);
        List<Setting.RotorPosition> rotors = setting.getActiveRotors();

        rotorsStep(rotors);
        intermediate = moveForward(rotors, intermediate);
        intermediate = setting.getReflector().reflect(intermediate + 1) - 1;
        intermediate = moveBackward(rotors, intermediate);
        char latterBeforePlugboard = keyboard.lightALamp(intermediate);
        return setting.getPlugboard().plugboardSwap(latterBeforePlugboard);
    }

    private static int moveBackward(List<Setting.RotorPosition> rotors, int intermediate) {
        for (int i = rotors.size() - 1; i >= 0; i--) {
            intermediate = rotors.get(i).rotor.mapping(intermediate, Direction.BACKWARD);
        }
        return intermediate;
    }

    private static int moveForward(List<Setting.RotorPosition> rotors, int intermediate) {
        for (int i = 0; i < rotors.size(); i++) {
            intermediate = rotors.get(i).rotor.mapping(intermediate, Direction.FORWARD);
        }
        return intermediate;
    }

    private void rotorsStep(List<Setting.RotorPosition> rotors) {
        boolean shouldStepNext = false;
        int rotorIndex = 0;
        do {
            Rotor rotor = rotors.get(rotorIndex).rotor;
            rotor.step();
            shouldStepNext = rotor.atNotch();
            rotorIndex++;
        } while (shouldStepNext && rotorIndex < rotors.size());
    }
}
