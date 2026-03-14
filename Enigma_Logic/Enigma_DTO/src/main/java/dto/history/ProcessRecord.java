package dto.history;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcessRecord implements Serializable {
    public final String sorceMessage;
    public final String processedMessage;
    public final String currentCode;
    private final String machineName;
    private final long timeInNanos;

    public ProcessRecord(String sorceMessage, String processedMessage, long timeInNanos, String currentCode, String machineName) {
        this.sorceMessage = sorceMessage;
        this.processedMessage = processedMessage;
        this.timeInNanos = timeInNanos;
        this.currentCode = currentCode;
        this.machineName = machineName;
    }


}
