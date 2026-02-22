package dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ManualConfigurationModel {
    private final String line;
    private final String initialRotorsPositions;
    private final int reflectorId;
    private final String plugboardInput;
}




