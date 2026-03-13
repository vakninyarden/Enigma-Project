package dto.config.details;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class RotorSnapshot {
    int rotorId;
    int originalPosition;
    int currentPosition;
    int notchIndex;
    int alphabetSize;

    char originalLetter;
    char currentLetter;
}
