package patmal.course.enigma;


import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Rotor;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import patmal.course.enigma.machine.MachineEntity;
import patmal.course.enigma.machine.MachineRepository;
import patmal.course.enigma.processing.ProcessingRepository;
import patmal.course.enigma.reflector.ReflectorEntity;
import patmal.course.enigma.reflector.ReflectorToReflectorPersistentEntityConverter;
import patmal.course.enigma.rotor.RotorEntity;
import patmal.course.enigma.rotor.RotorRepository;
import patmal.course.enigma.rotor.RotorToRotorPersistentEntityConverter;
import repository.Repository;

import java.util.Map;
import java.util.UUID;

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


    public void saveXmlToDb(Repository repo, String machineName) {

        if (machineRepository.existsByName(machineName)) {
            throw new RuntimeException("Machine already exists: " + machineName);
        }
        MachineEntity machineEntity =
                new MachineEntity(machineName,
                        repo.getRotorCount(),
                        repo.getAbc());

        for (Rotor rotor : repo.getRotors().values()) {

            RotorEntity rotorEntity =
                    RotorToRotorPersistentEntityConverter.convert(rotor);

            machineEntity.addRotor(rotorEntity);
        }

        for (Reflector reflector : repo.getReflectors().values()) {

            ReflectorEntity reflectorEntity =
                    ReflectorToReflectorPersistentEntityConverter.convert(reflector);
            machineEntity.addReflector(reflectorEntity);
        }
        machineRepository.save(machineEntity);
    }


}
