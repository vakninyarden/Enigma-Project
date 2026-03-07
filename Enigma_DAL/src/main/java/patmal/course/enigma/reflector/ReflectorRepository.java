package patmal.course.enigma.reflector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReflectorRepository extends JpaRepository<ReflectorEntity, UUID> {
}
