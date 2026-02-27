package patmal.course.enigma.session;

import engine.Engine;
import engine.EngineImpl;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MachineStoreManager {

    //name to engine mapping
    private static Map<String, Engine> machineStore = new ConcurrentHashMap<>();


        public static void addMachine(String machineName, Engine engine) {
            machineStore.put(machineName, engine);
        }

        public static Engine getMachine(String machineName) {
            if(!machineStore.containsKey(machineName)){
                throw new IllegalArgumentException("Unknown machine name: " + machineName);
            }
            return machineStore.get(machineName);
        }


}
