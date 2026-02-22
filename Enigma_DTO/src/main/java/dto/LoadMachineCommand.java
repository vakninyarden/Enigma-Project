package dto;
import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@Data
public class LoadMachineCommand {

    private final InputStream inputStream;
}