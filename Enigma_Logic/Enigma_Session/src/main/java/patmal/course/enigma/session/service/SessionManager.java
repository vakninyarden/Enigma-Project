package patmal.course.enigma.session.service;

import engine.Engine;
import engine.EngineImpl;
import org.springframework.stereotype.Service;
import patmal.course.enigma.PersistanceService;
import repository.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManager {
    private Map<String, Engine> enginesBySession = new ConcurrentHashMap<>();
    private Map<String, String> machineNameBySession = new ConcurrentHashMap<>();
    private final PersistanceService persistanceService;

    public SessionManager(PersistanceService persistanceService) {
        this.persistanceService = persistanceService;
    }


    public String createSession(String machineName) {
        Repository repository = persistanceService.getRepositoryFromDb(machineName);

        Engine engine = new EngineImpl(repository);

        String sessionId = java.util.UUID.randomUUID().toString();

        enginesBySession.put(sessionId, engine);
        machineNameBySession.put(sessionId, machineName);


        return sessionId;
    }

    public void deleteSession(String sessionId) {
        if (!enginesBySession.containsKey(sessionId)) {
            throw new IllegalArgumentException("Unknown session ID: " + sessionId);
        }
        machineNameBySession.remove(sessionId);
        enginesBySession.remove(sessionId);
    }

    public Engine getEngineBySessionId(String sessionId) {
        if (!enginesBySession.containsKey(sessionId)) {
            throw new IllegalArgumentException("Unknown session ID: " + sessionId);
        }
        return enginesBySession.get(sessionId);
    }


}
