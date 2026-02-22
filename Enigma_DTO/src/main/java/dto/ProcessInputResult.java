
package dto;
import lombok.Builder;
import lombok.Data;

@Data
public class ProcessInputResult {

    private final String output;
    private final String currentRotorsPositionCompact;

}