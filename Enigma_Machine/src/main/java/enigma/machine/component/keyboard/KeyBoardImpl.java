package enigma.machine.component.keyboard;
import java.io.Serializable;

public class KeyBoardImpl implements KeyBoard , Serializable {
    private String alphabet;

    public KeyBoardImpl(String alphabet) {
        this.alphabet = alphabet;
    }
    @Override
    public int processCharacter(char c) {
        return alphabet.indexOf(c);
    }

    @Override
    public char lightALamp(int input) {
        return alphabet.charAt(input);
    }


}
