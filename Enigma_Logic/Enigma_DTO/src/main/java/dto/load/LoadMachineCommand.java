package dto.load;
import lombok.Data;

import java.io.InputStream;

@Data
public class LoadMachineCommand {

    private final InputStream inputStream;
}