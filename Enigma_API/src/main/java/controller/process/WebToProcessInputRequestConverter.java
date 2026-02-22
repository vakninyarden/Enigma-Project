
package controller.process;
import dto.ProcessInputModel;
import org.springframework.stereotype.Component;

// to disconnect the web layer from the engine layer, we use this converter to convert the web request to a model that the engine can understand
@Component
public class WebToProcessInputRequestConverter {
    public ProcessInputModel convert(String input) {
        return new ProcessInputModel(input);
    }
}