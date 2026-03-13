package dto.config.details;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MachineSnapshot {

     int  totalRotors;
     int   totalReflectors;
     int  totalProcessedMessages;

    String originalCodeCompact;
    String currentCodeCompact;

    List<RotorSnapshot> rotors;
    String reflectorId;
    Map<Character,Character> plugboard;

  /*  public MachineSnapshot(int totalRotors, int totalReflectors, int totalProcessedMessages) {
        this.totalRotors = totalRotors;
        this.totalReflectors = totalReflectors;
        this.totalProcessedMessages = totalProcessedMessages;
    }*/
/*

    public MachineSnapshot(int totalRotors, int totalReflectors, int totalProcessedMessages, String originalCodeCompact, String currentCodeCompact, List<RotorSnapshot> rotors,String reflectorId, Map<Character,Character> plugboard) {
        this.totalRotors = totalRotors;
        this.totalReflectors = totalReflectors;
        this.totalProcessedMessages = totalProcessedMessages;
        this.originalCodeCompact = originalCodeCompact;
        this.currentCodeCompact = currentCodeCompact;
        this.rotors = rotors;
        this.reflectorId = reflectorId;
        this.plugboard = plugboard;
    }
*/

}
