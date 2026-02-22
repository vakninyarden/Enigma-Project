package controller.config;

import dto.DtoMachineSpecification;
import dto.ManualConfigurationModel;
import engine.Engine;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import patmal.course.enigma.api.EnigmaApi;
import org.springframework.http.ResponseEntity;
import  patmal.course.enigma.api.model.GetCurrentMachineStatus200Response;
import patmal.course.enigma.api.model.EnigmaManualConfigRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enigma/config")
public class ConfigController  {

    private final Engine engine;
    private final WebToManualConfigurationRequestConverter manualConverter;
    private final MachineDetailsResponseToWebConverter detailsConverter;


    @PutMapping("/manual")
    public ResponseEntity<String>
    setManualCodeSelection(@RequestBody EnigmaManualConfigRequest request) {
        {
            try {

                // 1️⃣ Web → Internal Command
                ManualConfigurationModel model =
                        manualConverter.convert(request);

                // 2️⃣ Call Engine
                engine.codeManual(
                        model.getLine(),
                        model.getInitialRotorsPositions(),
                        model.getReflectorId(),
                        model.getPlugboardInput()
                );

                return ResponseEntity.ok("Manual configuration applied successfully");

            } catch (Exception e) {

                return ResponseEntity.badRequest().body(e.getMessage());

            }
        }
    }

    @PutMapping("/automatic")
    public ResponseEntity<String>
    setAutomaticCodeSetup(@RequestParam("sessionID") String sessionID) {
        try {

            // בעתיד:
            // Engine engine = sessionManager.getEngine(sessionID);

            String generatedCode = engine.codeAuto();

            return ResponseEntity.ok(generatedCode);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/reset")
    public ResponseEntity<String>
    resetToOriginalCode(@RequestParam("sessionID") String sessionID) {
        try {

            // בעתיד:
            // Engine engine = sessionManager.getEngine(sessionID);

            // 1️⃣ Reset machine
            engine.resetCode();

            // 2️⃣ קבלת המצב לאחר reset
            DtoMachineSpecification spec = engine.showMachineDetails();

            // 3️⃣ החזרת הקוד המקורי הקומפקטי
            return ResponseEntity.ok(spec.getOriginalCode());

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

}


