package validator;

import exception.inputexception.InputValidationException;
import org.springframework.stereotype.Component;


public class OrderOperationValidator {


    public void validateCodeSet(boolean isCodeSet) {
        if (!isCodeSet) {
            throw new InputValidationException("Code not set. Please set the code manually or automatically before processing messages.");
        }
    }
}
