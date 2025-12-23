package enigma.machine.component.rotor;

import java.util.List;
import java.io.Serializable;

public class RotorImpl implements Rotor, Serializable {
    private final int id;
    private final int notchPosition;
    private int currentPosition;
    private int originalPosition;
    private final List<Character> rightMapping;
    private final List<Character> leftMapping;

    public RotorImpl(int id, int notchPosition,
                     List<Character> rightMapping, List<Character> leftMapping) {
        this.rightMapping = rightMapping;
        this.leftMapping = leftMapping;
        this.originalPosition = 0;
        this.id = id;
        this.notchPosition = notchPosition;
        this.currentPosition = 0;
    }

    @Override
    public void setOriginalPosition(int originalPosition) {
        this.originalPosition = originalPosition;
        this.currentPosition = originalPosition;
    }


    @Override
    public int getRotorId() {
        return id;
    }

    @Override
    public int getNotchIndex() {
        return notchPosition;
    }

    @Override
    public int mapping(int indexInRotor, Direction direction) {
        int sizeOfAlphabet = rightMapping.size();
        int shifted = (indexInRotor + currentPosition) % sizeOfAlphabet;
        char outChar;
        int mapped;
        if (direction == Direction.FORWARD) {
            outChar = rightMapping.get(shifted);
            mapped = leftMapping.indexOf(outChar);
        } else {
            outChar = leftMapping.get(shifted);
            mapped = rightMapping.indexOf(outChar);
        }
        return (mapped - currentPosition + sizeOfAlphabet) % sizeOfAlphabet;
    }


    @Override
    public boolean atNotch() {
        return currentPosition == notchPosition;
    }

    @Override
    public void step() {
        int sizeOfAlphabet = rightMapping.size();
        currentPosition = (currentPosition + 1) % sizeOfAlphabet;
    }

    @Override
    public void reset() {
        currentPosition = originalPosition;
    }

    public List<Character> getRightMapping() {
        return rightMapping;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getOriginalPosition() {
        return originalPosition;
    }
}

