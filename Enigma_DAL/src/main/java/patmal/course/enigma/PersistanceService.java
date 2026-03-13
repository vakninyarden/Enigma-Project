package patmal.course.enigma;


import dto.ProcessRecord;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Rotor;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import patmal.course.enigma.machine.MachineEntity;
import patmal.course.enigma.machine.MachineRepository;
import patmal.course.enigma.processing.ProcessingEntity;
import patmal.course.enigma.processing.ProcessingRepository;
import patmal.course.enigma.reflector.ReflectorEntity;
import patmal.course.enigma.reflector.ReflectorEntityToReflectorConverter;
import patmal.course.enigma.reflector.ReflectorToReflectorPersistentEntityConverter;
import patmal.course.enigma.rotor.RotorEntity;
import patmal.course.enigma.rotor.RotorEntityToRotorConverter;
import patmal.course.enigma.rotor.RotorRepository;
import patmal.course.enigma.rotor.RotorToRotorPersistentEntityConverter;
import repository.Repository;

import java.util.HashMap;
import java.util.List;
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

    public Repository getRepositoryFromDb(String machineName) {

            Map<Integer, Rotor> rotors = new HashMap<>();
            Map<String, Reflector> reflectors = new HashMap<>();

            MachineEntity machineEntity = getMachineByName(machineName);

            for (RotorEntity rotorEntity : machineEntity.getRotors()) {

                Rotor rotor =
                        RotorEntityToRotorConverter.convert(rotorEntity);

                rotors.put(rotor.getRotorId(), rotor);
            }

            for (ReflectorEntity reflectorEntity : machineEntity.getReflectors()) {

                Reflector reflector =
                        ReflectorEntityToReflectorConverter.convert(reflectorEntity);

                reflectors.put(reflector.getReflectorId(), reflector);
            }

            return new Repository(
                    machineEntity.getAbc(),
                    rotors,
                    reflectors,
                    machineEntity.getRotorsCount(),
                    machineEntity.getName()
            );

    }

    public MachineEntity getMachineByName(String machineName) {
        return machineRepository.findByName(machineName)
                .orElseThrow(() -> new RuntimeException("Machine not found: " + machineName));
    }

    public void saveProcessingRecordToDb(ProcessRecord record,String sessionId) {
        MachineEntity machineEntity = getMachineByName(record.getMachineName());

     /*   ProcessingEntity processingEntity =
                ProcessingEntity.builder()
                        .id(UUID.randomUUID())
                        .sessionId(sessionId)
                        .code(record.currentCode)
                        .inputText(record.getSorceMessage())
                        .outputText(record.getProcessedMessage())
                        .timeNs(record.getTimeInNanos())
                        .machine(machineEntity)
                        .build();
*/

        ProcessingEntity processingEntity = new ProcessingEntity();
        processingEntity.setId(UUID.randomUUID());
        processingEntity.setSessionId(sessionId);
        processingEntity.setCode(record.getCurrentCode());
        processingEntity.setInputText(record.getSorceMessage());
        processingEntity.setOutputText(record.getProcessedMessage());
        processingEntity.setTimeNs(record.getTimeInNanos());
        processingEntity.setMachine(machineEntity);

        processingRepository.save(processingEntity);
    }

    public List<ProcessRecord> getHistoryBySessionId(String sessionId) {
        return processingRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("No records found for session ID: " + sessionId))
                .stream()
                .map(entity -> new ProcessRecord(
                        entity.getInputText(),
                        entity.getOutputText(),
                        entity.getTimeNs(),
                        entity.getCode(),
                        entity.getMachine().getName()
                ))
                .toList();

    }

    public List<ProcessRecord> getHistoryByMachineName(String machineName) {
        MachineEntity machineEntity = getMachineByName(machineName);
        return processingRepository.findAll()
                .stream()
                .filter(entity -> entity.getMachine().getId().equals(machineEntity.getId()))
                .map(entity -> new ProcessRecord(
                        entity.getInputText(),
                        entity.getOutputText(),
                        entity.getTimeNs(),
                        entity.getCode(),
                        entity.getMachine().getName()
                ))
                .toList();
    }









}
