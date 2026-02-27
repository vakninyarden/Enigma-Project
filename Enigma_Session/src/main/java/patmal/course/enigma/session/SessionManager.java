package patmal.course.enigma.session;

import engine.Engine;
import engine.EngineImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import patmal.course.enigma.session.dto.SessionDTO;
import repository.Repository;
import repository.XMLRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManager {
    // sessionId to machine name mapping
        private static final Map<String, String> sessions = new ConcurrentHashMap<>();


        public String createSession(String machineName) {
            String sessionId = java.util.UUID.randomUUID().toString();
            Repository repository = XMLRepository.getRepository(machineName);
            Engine engine = new EngineImpl(repository);
            MachineStoreManager.addMachine(machineName, engine);

            sessions.put(sessionId, machineName);
            return sessionId;
        }

        public void deleteSession(String sessionId) {
            if(!sessions.containsKey(sessionId)){
                throw new IllegalArgumentException("Unknown session ID: " + sessionId);
            }
            sessions.remove(sessionId);
        }

        public  Engine getEngineBySessionId(String sessionId) {
            if(!sessions.containsKey(sessionId)){
                throw new IllegalArgumentException("Unknown session ID: " + sessionId);
            }
            return MachineStoreManager.getMachine(sessions.get(sessionId));
        }




}
