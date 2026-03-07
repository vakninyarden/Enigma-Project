package patmal.course.enigma.rotor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import patmal.course.enigma.reflector.ReflectorEntity;

import java.util.UUID;

@Repository
public interface RotorRepository extends JpaRepository<RotorEntity, UUID> {
}
