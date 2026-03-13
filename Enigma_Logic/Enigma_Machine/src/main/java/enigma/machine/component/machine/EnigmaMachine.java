package enigma.machine.component.machine;

import enigma.machine.component.setting.Setting;

public interface EnigmaMachine {
    void resetMachine();

    char processLatter(char message);

    Setting getSetting();

}
