package controller.config;


import dto.requests.config.CreateManualConfigRequestDto;
import dto.response.config.ManualConfigToWeb;
import engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enigma/config") //the url for the controller
public class ConfigController {
    private final Engine engine;

    @Autowired
    public ConfigController(Engine engine) {
        this.engine = engine;
    }

  /*  @PutMapping("/manual")
    public ResponseEntity<ManualConfigToWeb> setManualConfig(@RequestBody CreateManualConfigRequestDto ConfigRequest) {
        try {
            engine.codeManual(

            );

        } catch (Exception e) {
            e.printStackTrace();
            ManualConfigToWeb errorResponse = ManualConfigToWeb.builder()
                    .success(false)
                    .FeedbackMessage("Failed to update manual configuration: " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }*/

}
