package validator;

import bte.component.jaxb.*;
import exception.fileexceoption.*;

import java.io.File;
import java.util.*;

public class XmlFileValidator implements FileValidator {

    public boolean isFileExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new exception.fileexceoption.FileDoesNotExistException(filePath);
        }
        return true;
    }

    public boolean validateIsXmlFile(String filePath) {
        if (!filePath.toLowerCase().endsWith(".xml")) {
            throw new exception.fileexceoption.NotXmlFileException(filePath);
        }
        return true;

    }

    @Override
    public boolean isAbcSizeEven(String abc) {
        if (abc.length() % 2 != 0) {
            throw new exception.fileexceoption.AbcSizeNotEvenException();
        }
        return true;
    }

    @Override
    public boolean hasAtLeastThreeRotors(List<BTERotor> rotors) {
        if (rotors.size() < 3) {
            throw new exception.fileexceoption.NotEnoughRotorsException(rotors.size());
        }
        return true;
    }

    @Override
    public boolean hasValidRotorIds(List<BTERotor> rotors) {

        if (rotors == null || rotors.isEmpty()) {
            throw new exception.fileexceoption.InvalidRotorIdsException();
        }
        Set<Integer> ids = new HashSet<>();
        int maxId = 0;
        for (BTERotor rotor : rotors) {
            int id = rotor.getId();
            if (id <= 0) {
                throw new exception.fileexceoption.InvalidRotorIdsException();
            }
            if (!ids.add(id)) {
                throw new exception.fileexceoption.InvalidRotorIdsException();
            }

            maxId = Math.max(maxId, id);
        }

        if (maxId != ids.size()) {
            throw new exception.fileexceoption.InvalidRotorIdsException();
        }
        return true;
    }

    @Override
    public boolean hasNoDuplicateMappingsInRotor(List<BTERotor> rotors) {

        for (BTERotor rotor : rotors) {

            Set<Character> leftInputs = new HashSet<>();
            Set<Character> rightInputs = new HashSet<>();

            for (BTEPositioning p : rotor.getBTEPositioning()) {
                String left = p.getLeft();
                for (int i = 0; i < left.length(); i++) {
                    if (!leftInputs.add(left.charAt(i))) {
                        throw new exception.fileexceoption.DuplicateRotorMappingException(rotor.getId());
                    }
                }

                String right = p.getRight();
                for (int i = 0; i < right.length(); i++) {
                    if (!rightInputs.add(right.charAt(i))) {
                        throw new exception.fileexceoption.DuplicateRotorMappingException(rotor.getId());
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean isNotchPositionInRange(List<BTERotor> rotors, String abc) {

        for (BTERotor rotor : rotors) {
            int notchPosition = rotor.getNotch();
            if (notchPosition < 0 || notchPosition > abc.length()) {
                throw new exception.fileexceoption.NotchOutOfRangeException(rotor.getId());
            }
        }

        return true;
    }

    @Override
    public boolean hasValidReflectorIds(List<BTEReflector> reflectors) {

        if (reflectors == null || reflectors.isEmpty()) {
            throw new exception.fileexceoption.InvalidReflectorIdException("NULL/EMPTY");
        }

        Set<Integer> ids = new HashSet<>();
        int maxId = 0;

        for (BTEReflector reflector : reflectors) {
            int id = romanToInt(reflector.getId());

            if (id <= 0) {
                throw new exception.fileexceoption.InvalidReflectorIdException(reflector.getId());
            }

            if (!ids.add(id)) {
                throw new exception.fileexceoption.InvalidReflectorIdException(reflector.getId());
            }

            maxId = Math.max(maxId, id);
        }

        if (maxId != ids.size()) {
            throw new exception.fileexceoption.InvalidReflectorIdException("Non-sequential IDs");
        }

        return true;
    }

    @Override
    public boolean hasNoSelfMappingInReflector(List<BTEReflector> reflectors) {

        for (BTEReflector refl : reflectors) {
            for (BTEReflect mapping : refl.getBTEReflect()) {
                if (mapping.getInput() == mapping.getOutput()) {
                    throw new exception.fileexceoption.ReflectorSelfMappingException(
                            String.valueOf(mapping.getInput()),
                            refl.getId()
                    );
                }
            }
        }

        return true;
    }

    public int romanToInt(String roman) {
        if (roman == null) {
            return -1;
        }

        switch (roman) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            default:
                return -1;
        }
    }

    public void ValidateAll(BTEEnigma bteEnigma) {
        isAbcSizeEven(bteEnigma.getABC());
        hasAtLeastThreeRotors(bteEnigma.getBTERotors().getBTERotor());
        hasValidRotorIds(bteEnigma.getBTERotors().getBTERotor());
        hasNoDuplicateMappingsInRotor(bteEnigma.getBTERotors().getBTERotor());
        isNotchPositionInRange(bteEnigma.getBTERotors().getBTERotor(), bteEnigma.getABC());
        hasValidReflectorIds(bteEnigma.getBTEReflectors().getBTEReflector());
        hasNoSelfMappingInReflector(bteEnigma.getBTEReflectors().getBTEReflector());
    }

    public void ValidateFilePath(String filePath) {
        validateIsXmlFile(filePath);
        isFileExists(filePath);
    }
}
