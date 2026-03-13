package patmal.course.enigma.processing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessingRepository extends JpaRepository<ProcessingEntity, UUID> {

    Optional<List<ProcessingEntity>> findBySessionId(String sessionId);

}
