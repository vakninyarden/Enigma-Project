package engine;

import dto.DtoMachineSpecification;
import dto.DtoStatistic;

import java.io.IOException;

public interface Engine {
    void loadXml(String path);

    DtoMachineSpecification showMachineDetails(boolean isCodeSet);

    String processMessage(String message);

    public void codeManual(String line, String initialRotorsPositions, int reflectorId);

    String codeAuto();

    void resetCode();

    DtoStatistic statistics();

    void saveMachineStateToFile(String path) throws IOException;

    void loadMachineStateFromFile(String path) throws IOException;
}
