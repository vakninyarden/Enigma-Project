package repository;

import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.reflector.ReflectorImpl;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.rotor.RotorImpl;
import java.util.*;
import java.io.Serializable;


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
/*
    public Repository(String abc, BTEEnigma bteEnigma,int numberOfRotors) {
        this.abc = abc.toUpperCase();
        this.rotors = buildRotorsRepository(bteEnigma.getBTERotors(), this.abc);
        this.reflectors = buildReflectorsRepository(bteEnigma.getBTEReflectors());
        this.numberOfRotors = numberOfRotors;
    }
*/

    // Copy constructor for deep copy (currently shallow copy of maps)
 /*   public Repository(Repository other) {
        this.abc = other.abc;
        this.numberOfRotors = other.numberOfRotors;

        // SHALLOW copy של המפות (בשלב ראשון)
        this.rotors = new HashMap<>(other.rotors);
        this.reflectors = new HashMap<>(other.reflectors);
    }*/

    public String getAbc() {
        return abc;
    }

    public Rotor getRotor(int index) {
        return createRotor(index);
    }

    public Rotor createRotor(int index){
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
