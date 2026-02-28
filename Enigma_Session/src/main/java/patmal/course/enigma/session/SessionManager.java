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
    // dont need to be static map beacuse this is singelton
    private  Map<String, Engine> enginesBySession  = new ConcurrentHashMap<>();

    private   Map<String, String> machineNameBySession  = new ConcurrentHashMap<>();



    public String createSession(String machineName) {
            Repository repository = XMLRepository.getRepository(machineName);
// *********maybe need a deep copy*********
           //Repository repoForSession = repository.deepCopy();
           Engine engine = new EngineImpl(repository);

        String sessionId = java.util.UUID.randomUUID().toString();

        // we need to save each engine by his session id
        enginesBySession.put(sessionId, engine);
        machineNameBySession.put(sessionId, machineName);


      //  MachineStoreManager.addMachine(machineName, engine);

       // machineNameBySession .put(sessionId, machineName);
            return sessionId;
        }

        public void deleteSession(String sessionId) {
            if(!enginesBySession .containsKey(sessionId)){
                throw new IllegalArgumentException("Unknown session ID: " + sessionId);
            }
            machineNameBySession.remove(sessionId);
            enginesBySession.remove(sessionId);
        }

        public  Engine getEngineBySessionId(String sessionId) {
            if(!enginesBySession.containsKey(sessionId)){
                throw new IllegalArgumentException("Unknown session ID: " + sessionId);
            }
            return enginesBySession.get(sessionId);
            //  return MachineStoreManager.getMachine(machineNameBySession .get(sessionId));
        }

        public String getMachineNameBySessionId(String sessionId) {
            if(!machineNameBySession .containsKey(sessionId)){
                throw new IllegalArgumentException("Unknown session ID: " + sessionId);
            }
            return machineNameBySession.get(sessionId);
        }




}
