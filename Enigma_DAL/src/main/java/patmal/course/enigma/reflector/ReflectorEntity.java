package patmal.course.enigma.reflector;

import jakarta.persistence.*;
import lombok.*;
import patmal.course.enigma.machine.MachineEntity;

import java.util.UUID;


@Entity
@Table(name = "machines_reflectors")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReflectorEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "input_text", nullable = false)
    private String input;

    @Column(name = "output_text", nullable = false)
    private String output;

    @Column(name = "reflector_id", nullable = false)
    private String reflectorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private MachineEntity machine;


    public ReflectorEntity() {
        this.id = UUID.randomUUID();
    }
}
