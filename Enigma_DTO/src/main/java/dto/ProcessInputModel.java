package dto;

import lombok.Data;

//represents the intetnal request model for the process input command, it contains the input string to be processed by the machine
@Data
public class ProcessInputModel {
    private final String input;

}


