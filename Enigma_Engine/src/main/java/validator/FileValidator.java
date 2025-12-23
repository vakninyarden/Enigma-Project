package validator;

import bte.component.jaxb.BTEEnigma;
import bte.component.jaxb.BTEReflector;
import bte.component.jaxb.BTERotor;


import java.util.List;

public interface FileValidator {
    boolean isFileExists(String filePath);


    boolean isAbcSizeEven(String abc);

    boolean hasAtLeastThreeRotors(List<BTERotor> rotors);

    boolean hasValidRotorIds(List<BTERotor> rotors);

    boolean hasNoDuplicateMappingsInRotor(List<BTERotor> rotors);

    boolean isNotchPositionInRange(List<BTERotor> rotors, String abc);

    boolean hasValidReflectorIds(List<BTEReflector> reflectors);

    boolean hasNoSelfMappingInReflector(List<BTEReflector> reflector);

    void ValidateAll(BTEEnigma bteEnigma);
}
