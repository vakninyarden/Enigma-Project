package patmal.course.enigma.session.dto;

import engine.Engine;
import lombok.Data;

@Data
public class SessionDTO {
    private final String sessionId;
    private final String machineName;
    private final Engine engine;

    public SessionDTO(String sessionId, String machineName, Engine engine) {
        this.sessionId = sessionId;
        this.machineName = machineName;
        this.engine = engine;
    }

}
