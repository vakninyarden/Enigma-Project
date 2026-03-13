package engine;

import dto.*;
import dto.config.details.MachineSnapshot;

import java.io.IOException;


// testing commit
public interface Engine {

    DtoMachineSpecification showMachineDetails();

    ProcessRecord processMessage(String message);

    void codeManual(String line, String initialRotorsPositions, int reflectorId, String plugboardInput);

    String codeAuto();

    void resetCode();

    DtoStatistic statistics();

    void saveMachineStateToFile(String path) throws IOException;

    void loadMachineStateFromFile(String path) throws IOException;

    String getCurrentRotorPositions();

    MachineSnapshot getMachineSnapshot();


}