package patmal.course.enigma.service;


import dto.process.ProcessInputResult;
import engine.Engine;
import org.springframework.stereotype.Service;
import patmal.course.enigma.session.service.SessionManager;

@Service
public class ProcessService {
    private final SessionManager sessionManager;

    public ProcessService(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    public ProcessInputResult processInput(String sessionId,String input) {
        Engine engine = sessionManager.getEngineBySessionId(sessionId);
        String output = engine.processMessage(input);
        String RotorState = engine.getCurrentRotorPositions();
        return new ProcessInputResult(output, RotorState);
    }
}


