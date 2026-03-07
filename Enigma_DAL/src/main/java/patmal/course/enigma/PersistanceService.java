package patmal.course.enigma;


import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import patmal.course.enigma.machine.MachineRepository;
import patmal.course.enigma.processing.ProcessingRepository;

@Service
public class PersistanceService {

    private final MachineRepository machineRepository;
    private final ProcessingRepository processingRepository;
    private final EntityManager entityManager;

    public PersistanceService(MachineRepository machineRepository, ProcessingRepository processingRepository, EntityManager entityManager) {
        this.machineRepository = machineRepository;
        this.processingRepository = processingRepository;
        this.entityManager = entityManager;
    }
}
