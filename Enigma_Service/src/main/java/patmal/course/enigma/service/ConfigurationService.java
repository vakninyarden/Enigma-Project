package patmal.course.enigma.service;

import dto.ManualConfigurationModel;
import engine.Engine;
import org.springframework.stereotype.Service;
import patmal.course.enigma.session.SessionManager;

@Service
public class ConfigurationService {
    private Engine engine;

    public void test(SessionManager sessionManager , String sessionId, ManualConfigurationModel model)
    {

        engine = sessionManager.getEngineBySessionId(sessionId);
        engine.codeManual(
                model.getLine(),
                model.getInitialRotorsPositions(),
                model.getReflectorId(),
                model.getPlugboardInput()
        );
    }

}
