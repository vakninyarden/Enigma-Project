package patmal.course.enigma.machine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MachineRepository extends JpaRepository<MachineEntity, UUID> {
    boolean existsByName(String machineName);
    Optional<MachineEntity> findByName(String name);



}
