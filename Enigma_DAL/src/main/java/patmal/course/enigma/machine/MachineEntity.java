package patmal.course.enigma.machine;

import jakarta.persistence.*;
import lombok.*;
import patmal.course.enigma.processing.ProcessingEntity;
import patmal.course.enigma.reflector.ReflectorEntity;
import patmal.course.enigma.rotor.RotorEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "machines")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MachineEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "rotors_count", nullable = false)
    private Integer rotorsCount;

    @Column(name = "abc", nullable = false, length = 1024)
    private String abc;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RotorEntity> rotors = new ArrayList<>();

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReflectorEntity> reflectors = new ArrayList<>();

    @OneToMany(mappedBy = "machine")
    private List<ProcessingEntity> processingRecords;

    public MachineEntity() {
        this.id = UUID.randomUUID();
    }
    public MachineEntity(String name, int rotorsCount, String abc) {
        this();
        this.name = name;
        this.rotorsCount = rotorsCount;
        this.abc = abc;
    }


   /* public void addRotor(RotorEntity rotor) {
        rotor.setMachine(this);
        this.rotors.add(rotor);
    }

    public void addReflector(ReflectorEntity reflector) {
        reflector.setMachine(this);
        this.reflectors.add(reflector);
    }*/
}