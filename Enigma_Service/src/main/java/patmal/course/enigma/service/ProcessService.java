package patmal.course.enigma.service;


import dto.ProcessInputResult;
import engine.Engine;
import org.springframework.stereotype.Service;
import patmal.course.enigma.session.SessionManager;

@Service
public class ProcessService {
    SessionManager sessionManager;
    Engine engine;

    public ProcessInputResult processInput(String input,String sessionId) {

        engine = sessionManager.getEngineBySessionId(sessionId);

        String output = engine.processMessage(input);

        String RotorState = engine.getCurrentRotorPositions();

        return new ProcessInputResult(output, RotorState);

    }
}


