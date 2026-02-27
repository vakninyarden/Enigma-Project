package engine;

import dto.DtoMachineSpecification;
import dto.DtoStatistic;
import dto.LoadMachineCommand;
import dto.LoadMachineResult;

import java.io.InputStream;

import java.io.IOException;

public interface Engine {

    DtoMachineSpecification showMachineDetails();

    String processMessage(String message);

    void codeManual(String line, String initialRotorsPositions, int reflectorId, String plugboardInput);

    String codeAuto();

    void resetCode();

    DtoStatistic statistics();

    void saveMachineStateToFile(String path) throws IOException;

    void loadMachineStateFromFile(String path) throws IOException;


     String getCurrentRotorPositions() ;
    }
