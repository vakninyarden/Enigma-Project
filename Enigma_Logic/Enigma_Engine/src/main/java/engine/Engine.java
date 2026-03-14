package engine;

import dto.DtoMachineSpecification;
import dto.config.details.MachineSnapshot;
import dto.history.ProcessRecord;


public interface Engine {

    DtoMachineSpecification showMachineDetails();

    ProcessRecord processMessage(String message);

    void codeManual(String line, String initialRotorsPositions, int reflectorId, String plugboardInput);

    String codeAuto();

    void resetCode();

    String getCurrentRotorPositions();

    MachineSnapshot getMachineSnapshot();


}