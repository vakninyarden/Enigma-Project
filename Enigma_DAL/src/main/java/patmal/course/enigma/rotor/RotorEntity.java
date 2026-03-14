package patmal.course.enigma.rotor;

import jakarta.persistence.*;
import lombok.*;
import patmal.course.enigma.machine.MachineEntity;
import java.util.UUID;

@Entity
@Table(name = "machines_rotors")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class RotorEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;


    @Column(name = "rotor_id", nullable = false)
    private Integer rotorId;

    @Column(name = "notch", nullable = true)
    private Integer notch;

    @Column(name = "wiring_right", nullable = false)
    private String wiringRight;

    @Column(name = "wiring_left", nullable = false)
    private String wiringLeft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private MachineEntity machine;

    public RotorEntity() {
        this.id = UUID.randomUUID();
    }

    public RotorEntity(int rotorId, String rightWiring, String leftWiring, int notchPosition) {
        this();
        this.rotorId = rotorId;
        this.wiringRight = rightWiring;
        this.wiringLeft = leftWiring;
        this.notch = notchPosition;
    }

}
