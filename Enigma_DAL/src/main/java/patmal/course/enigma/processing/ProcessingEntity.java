package patmal.course.enigma.processing;
import jakarta.persistence.*;
import lombok.*;
import patmal.course.enigma.machine.MachineEntity;

import java.util.UUID;

@Entity
@Table(name = "processing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessingEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;


    //the code that the input encrpyted/decrypted with
    @Column(name = "code", nullable = false, length = 4000)
    private String code;

    @Column(name = "input", nullable = false, length = 4000)
    private String inputText;

    @Column(name = "output", nullable = false, length = 4000)
    private String outputText;

    @Column(name = "time", nullable = false)
    private Long timeNs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private MachineEntity machine;
}


