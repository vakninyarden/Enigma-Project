package repository;

import bte.component.jaxb.*;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.reflector.ReflectorImpl;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.rotor.RotorImpl;

import java.util.*;
import java.io.Serializable;

public class Repository implements Serializable {
    private final Map<Integer, Rotor> rotors;
    private final Map<String, Reflector> reflectors;
    private final String abc;

    public Repository(String abc, BTEEnigma bteEnigma) {
        this.abc = abc;
        this.rotors = buildRotorsRepository(bteEnigma.getBTERotors(), abc);
        this.reflectors = buildReflectorsRepository(bteEnigma.getBTEReflectors());
    }

    public String getAbc() {
        return abc;
    }

    public Rotor getRotor(int index) {
        return rotors.get(index);
    }


    public Reflector getReflecton(String id) {
        return reflectors.get(id);
    }

    private Map<Integer, Rotor> buildRotorsRepository(BTERotors bteRotors, String abc) {
        Map<Integer, Rotor> result = new HashMap<>();

        for (BTERotor bteRotor : bteRotors.getBTERotor()) {
            Rotor rotor = buildRotor(bteRotor, abc);
            result.put(rotor.getRotorId(), rotor);
        }
        return result;
    }

    public Rotor buildRotor(BTERotor bteRotor, String abc) {
        int id = bteRotor.getId();
        int notch = bteRotor.getNotch() - 1;

        int size = abc.length();
        List<Character> rightMapping = new ArrayList<>(size);
        List<Character> leftMapping = new ArrayList<>(size);

        for (BTEPositioning pos : bteRotor.getBTEPositioning()) {
            String leftStr = pos.getLeft();
            String rightStr = pos.getRight();

            char leftChar = leftStr.charAt(0);
            char rightChar = rightStr.charAt(0);
            rightMapping.add(rightChar);
            leftMapping.add(leftChar);

        }
        return new RotorImpl(id, notch, rightMapping, leftMapping);
    }

    public int getRotorCount() {
        return rotors.size();
    }

    public int getReflectorCount() {
        return reflectors.size();
    }


    private Map<String, Reflector> buildReflectorsRepository(BTEReflectors bteReflectors) {
        Map<String, Reflector> result = new HashMap<>();
        for (BTEReflector bteRef : bteReflectors.getBTEReflector()) {
            Reflector reflector = buildReflector(bteRef);
            String key = bteRef.getId();
            result.put(key, reflector);
        }
        return result;

    }

    public Reflector buildReflector(BTEReflector bteReflector) {
        String xmlId = bteReflector.getId();
        Map<Integer, Integer> mapping = new HashMap<>();
        for (BTEReflect pair : bteReflector.getBTEReflect()) {
            int in = pair.getInput();
            int out = pair.getOutput();
            mapping.put(in, out);
            mapping.put(out, in);
        }

        return new ReflectorImpl(xmlId, mapping);
    }


}
