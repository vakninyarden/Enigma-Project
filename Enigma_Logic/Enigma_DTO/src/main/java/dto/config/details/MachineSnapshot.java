package dto.config.details;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MachineSnapshot {

    int totalRotors;
    int totalReflectors;
    int totalProcessedMessages;

    String originalCodeCompact;
    String currentCodeCompact;

    List<RotorSnapshot> rotors;
    String reflectorId;
    Map<Character, Character> plugboard;


}
