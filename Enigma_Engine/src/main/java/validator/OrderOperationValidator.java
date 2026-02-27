package validator;

import exception.inputexception.InputValidationException;
import org.springframework.stereotype.Component;

@Component
public class OrderOperationValidator {


    public void validateMachineLoaded(boolean isMachineLoaded) {
        if (!isMachineLoaded) {
            throw new InputValidationException("Machine not loaded. Please load an XML configuration first.");
        }
    }

    public void validateCodeSet(boolean isCodeSet) {
        if (!isCodeSet) {
            throw new InputValidationException("Code not set. Please set the code manually or automatically before processing messages.");
        }
    }
}
