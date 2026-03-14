package patmal.course.enigma;

import bte.component.jaxb.*;
import dto.load.LoadMachineCommand;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.reflector.ReflectorImpl;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.rotor.RotorImpl;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;
import patmal.course.enigma.validator.XmlFileValidator;
import repository.Repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class LoadManager {
    private XmlFileValidator xmlValidator = new XmlFileValidator();


    public BTEEnigma loadXmlFromStream(InputStream inputStream) {
        try {
            BTEEnigma machine = deserializeFrom(inputStream);
            cleanMachine(machine);
            return machine;
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse XML", e);
        }
    }

    private BTEEnigma deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(BTEEnigma.class);
        Unmarshaller u = jc.createUnmarshaller();
        return (BTEEnigma) u.unmarshal(in);
    }

    private void cleanMachine(BTEEnigma machine) {
        if (machine == null) {
            return;
        }

        String abc = machine.getABC();
        if (abc != null) {
            machine.setABC(abc.trim());

        }
    }

    public Repository loadXml(LoadMachineCommand command) {
        InputStream inputStream = command.getInputStream();
        BTEEnigma bteMachine = loadXmlFromStream(inputStream);
        xmlValidator.ValidateAll(bteMachine);
        Repository repository = buildRepo(bteMachine);
        return repository;
    }


    private Repository buildRepo(BTEEnigma bteEnigma) {

        Map<Integer, Rotor> rotors;
        Map<String, Reflector> reflectors;

        rotors = buildRotorsRepository(bteEnigma.getBTERotors(), bteEnigma.getABC());
        reflectors = buildReflectorsRepository(bteEnigma.getBTEReflectors());
        return new Repository(bteEnigma.getABC(), rotors, reflectors, bteEnigma.getRotorsCount().intValue(), bteEnigma.getName());

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
            String leftStr = pos.getLeft().toUpperCase();
            String rightStr = pos.getRight().toUpperCase();

            char leftChar = leftStr.charAt(0);
            char rightChar = rightStr.charAt(0);
            rightMapping.add(rightChar);
            leftMapping.add(leftChar);

        }
        return new RotorImpl(id, notch, rightMapping, leftMapping);
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


};
