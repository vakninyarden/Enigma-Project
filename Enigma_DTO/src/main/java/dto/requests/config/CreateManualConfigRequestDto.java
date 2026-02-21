package dto.requests.config;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CreateManualConfigRequestDto {
    private String machineName;
    private List<String> rotors;
    private List<Integer> rotorIds;
    private List<Character> rotorPositions;
}
