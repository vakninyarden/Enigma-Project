package patmal.course.enigma.service;

import dto.DtoMachineSpecification;
import dto.ManualConfigurationModel;
import dto.snapshot.MachineSnapshot;
import engine.Engine;
import org.springframework.stereotype.Service;
import patmal.course.enigma.session.SessionManager;

@Service
public class ConfigurationService {
    // private Engine engine;
    private final SessionManager sessionManager;

    public ConfigurationService(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    public DtoMachineSpecification GetCompactCurrentMachineDetailsService(String sessionId) {
        Engine engine = sessionManager.getEngineBySessionId(sessionId);
        return engine.showMachineDetails();
    }
 /*   public String getRotorsPos(String sessionId) {
        Engine engine = sessionManager.getEngineBySessionId(sessionId);
        return engine.getCurrentRotorPositions();
    }*/

    public MachineSnapshot getSnapshot(String sessionId) {
        Engine engine = sessionManager.getEngineBySessionId(sessionId);
        return engine.getMachineSnapshot();
    }

    public void CodeManualService(String sessionId, ManualConfigurationModel model)
    {
        Engine engine = sessionManager.getEngineBySessionId(sessionId);
        engine.codeManual(
                model.getLine(),
                model.getInitialRotorsPositions(),
                model.getReflectorId(),
                model.getPlugboardInput()
        );
    }
    public String CodeAutoService(String sessionId)
    {
        Engine engine = sessionManager.getEngineBySessionId(sessionId);
        return engine.codeAuto();
    }
    public DtoMachineSpecification ResetCodeService(String sessionId)
    {
        Engine engine = sessionManager.getEngineBySessionId(sessionId);
        engine.resetCode();
        return engine.showMachineDetails();
    }

}
