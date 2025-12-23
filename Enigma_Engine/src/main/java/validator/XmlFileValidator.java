package validator;

import bte.component.jaxb.*;
import exception.fileexception.*;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

public class XmlFileValidator implements FileValidator {

    public boolean isFileExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new exception.fileexception.FileDoesNotExistException(filePath);
        }
        return true;
    }

    public boolean validateIsXmlFile(String filePath) {
        if (!filePath.toLowerCase().endsWith(".xml")) {
            throw new exception.fileexception.NotXmlFileException(filePath);
        }
        return true;

    }

    @Override
    public boolean isAbcSizeEven(String abc) {
        if (abc.length() % 2 != 0) {
            throw new exception.fileexception.AbcSizeNotEvenException();
        }
        return true;
    }

    @Override
    public boolean hasAtLeastThreeRotors(List<BTERotor> rotors) {
        if (rotors.size() < 3) {
            throw new exception.fileexception.NotEnoughRotorsException(rotors.size());
        }
        return true;
    }

    @Override
    public boolean hasValidRotorIds(List<BTERotor> rotors) {

        if (rotors == null || rotors.isEmpty()) {
            throw new exception.fileexception.InvalidRotorIdsException();
        }
        Set<Integer> ids = new HashSet<>();
        int maxId = 0;
        for (BTERotor rotor : rotors) {
            int id = rotor.getId();
            if (id <= 0) {
                throw new exception.fileexception.InvalidRotorIdsException();
            }
            if (!ids.add(id)) {
                throw new exception.fileexception.InvalidRotorIdsException();
            }

            maxId = Math.max(maxId, id);
        }

        if (maxId != ids.size()) {
            throw new exception.fileexception.InvalidRotorIdsException();
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
                        throw new exception.fileexception.DuplicateRotorMappingException(rotor.getId());
                    }
                }

                String right = p.getRight();
                for (int i = 0; i < right.length(); i++) {
                    if (!rightInputs.add(right.charAt(i))) {
                        throw new exception.fileexception.DuplicateRotorMappingException(rotor.getId());
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
                throw new exception.fileexception.NotchOutOfRangeException(rotor.getId());
            }
        }

        return true;
    }

    @Override
    public boolean hasValidReflectorIds(List<BTEReflector> reflectors) {

        if (reflectors == null || reflectors.isEmpty()) {
            throw new exception.fileexception.InvalidReflectorIdException("NULL/EMPTY");
        }

        Set<Integer> ids = new HashSet<>();
        int maxId = 0;

        for (BTEReflector reflector : reflectors) {
            int id = romanToInt(reflector.getId());

            if (id <= 0) {
                throw new exception.fileexception.InvalidReflectorIdException(reflector.getId());
            }

            if (!ids.add(id)) {
                throw new exception.fileexception.InvalidReflectorIdException(reflector.getId());
            }

            maxId = Math.max(maxId, id);
        }

        if (maxId != ids.size()) {
            throw new exception.fileexception.InvalidReflectorIdException("Non-sequential IDs");
        }

        return true;
    }

    @Override
    public boolean hasNoSelfMappingInReflector(List<BTEReflector> reflectors) {

        for (BTEReflector refl : reflectors) {
            for (BTEReflect mapping : refl.getBTEReflect()) {
                if (mapping.getInput() == mapping.getOutput()) {
                    throw new exception.fileexception.ReflectorSelfMappingException(
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

    private void validateNumberOfActiveRotors(BigInteger activeRotorsCount, int totalRotorsCount ) {
        if (activeRotorsCount.compareTo(BigInteger.valueOf(totalRotorsCount)) > 0) {
            throw new RotorCountOutOfRangeException(activeRotorsCount.toString() + " active rotors for " + totalRotorsCount + " available rotors.");
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
        validateNumberOfActiveRotors(bteEnigma.getRotorsCount(), bteEnigma.getBTERotors().getBTERotor().size());
    }

    public void ValidateFilePath(String filePath) {
        validateIsXmlFile(filePath);
        isFileExists(filePath);
    }
}
