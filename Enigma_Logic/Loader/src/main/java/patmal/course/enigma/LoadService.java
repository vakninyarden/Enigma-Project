
package patmal.course.enigma;

import dto.load.LoadMachineCommand;
import dto.load.LoadMachineResult;
import org.springframework.stereotype.Service;
import repository.Repository;

@Service
public class LoadService {
    private LoadManager loadManager;
    private PersistanceService persistanceService;

    public LoadService(LoadManager loadManager, PersistanceService persistanceService) {
        this.loadManager = loadManager;
        this.persistanceService = persistanceService;
    }

    public LoadMachineResult loadMachine(LoadMachineCommand command) {
        Repository repository = loadManager.loadXml(command);
        String MachineName = repository.getMachineName();
        persistanceService.saveXmlToDb(repository, MachineName);
        return new LoadMachineResult(MachineName);

    }
}

