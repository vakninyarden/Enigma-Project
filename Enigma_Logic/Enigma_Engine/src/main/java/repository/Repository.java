package repository;

import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.rotor.RotorImpl;

import java.io.Serializable;
import java.util.Map;


public class Repository implements Serializable {
    int numberOfRotors;
    private final Map<Integer, Rotor> rotors;
    private final Map<String, Reflector> reflectors;
    private final String abc;
    private final String MachineName;


    public Repository(String abc, Map<Integer, Rotor> rotors, Map<String, Reflector> reflectors, int numberOfRotors, String machineName) {
        this.abc = abc.toUpperCase();
        this.rotors = rotors;
        this.reflectors = reflectors;
        this.numberOfRotors = numberOfRotors;
        this.MachineName = machineName;
    }


    public String getAbc() {
        return abc;
    }

    public Rotor getRotor(int index) {
        return createRotor(index);
    }

    public Rotor createRotor(int index) {
        Rotor newRotor = rotors.get(index);
        return new RotorImpl(newRotor.getRotorId(), newRotor.getNotchIndex(), newRotor.getRightMapping(), newRotor.getLeftMapping());
    }

    public Reflector getReflecton(String id) {
        return reflectors.get(id);
    }

    public int getRotorCount() {
        return rotors.size();
    }

    public int getReflectorCount() {
        return reflectors.size();
    }

    public int getNumberOfRotors() {
        return numberOfRotors;
    }

    public Map<Integer, Rotor> getRotors() {
        return rotors;
    }

    public Map<String, Reflector> getReflectors() {
        return reflectors;
    }

    public String getMachineName() {
        return MachineName;
    }
}
