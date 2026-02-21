package dto.response.loader;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EnigmaMachineWeb {
    boolean success;
    String machineName;
    String errorMessage;
}
