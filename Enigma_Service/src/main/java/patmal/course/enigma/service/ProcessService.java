package patmal.course.enigma.service;


import dto.ProcessRecord;
import dto.process.ProcessInputResult;
import engine.Engine;
import org.springframework.stereotype.Service;
import patmal.course.enigma.PersistanceService;
import patmal.course.enigma.session.service.SessionManager;

@Service
public class ProcessService {
    private final SessionManager sessionManager;
    private final PersistanceService persistanceService;

    public ProcessService(SessionManager sessionManager, PersistanceService persistanceService) {
        this.sessionManager = sessionManager;
        this.persistanceService = persistanceService;
    }

    public ProcessInputResult processInput(String sessionId, String input) {
        Engine engine = sessionManager.getEngineBySessionId(sessionId);

        try {
            ProcessRecord pr = engine.processMessage(input);

            persistanceService.saveProcessingRecordToDb(pr, sessionId);
            String RotorState = engine.getCurrentRotorPositions();

            return new ProcessInputResult(pr.getProcessedMessage(), RotorState);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}


