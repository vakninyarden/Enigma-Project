package controller.config;

import dto.DtoMachineSpecification;
import dto.ManualConfigurationModel;
import engine.Engine;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import patmal.course.enigma.api.model.EnigmaManualConfigRequest;
import patmal.course.enigma.service.ConfigurationService;
import patmal.course.enigma.session.SessionManager;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enigma/config")
public class ConfigController {

    private final SessionManager sessionManager;
    private final WebToManualConfigurationRequestConverter manualConverter;
    private final MachineDetailsResponseToWebConverter detailsConverter;
    private final ConfigurationService configurationService;

    @PutMapping("/manual")
    public ResponseEntity<String> setManualCodeSelection(@RequestBody EnigmaManualConfigRequest request) {
        ManualConfigurationModel model =
                manualConverter.convert(request);
        try {
            configurationService.test(sessionManager, request.getSessionID(), model);
       /* try {
                ManualConfigurationModel model =
                        manualConverter.convert(request);
                engine = sessionManager.getEngineBySessionId(request.getSessionID());
                engine.codeManual(
                        model.getLine(),
                        model.getInitialRotorsPositions(),
                        model.getReflectorId(),
                        model.getPlugboardInput()
                );*/


            return ResponseEntity.ok("Manual configuration applied successfully");
        } catch (
                Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }



  /*  @PutMapping("/automatic")
    public ResponseEntity<String>
    setAutomaticCodeSetup(@RequestParam("sessionID") String sessionID) {
        try {

            // בעתיד:
            engine = sessionManager.getEngineBySessionId(sessionID);

            String generatedCode = engine.codeAuto();

            return ResponseEntity.ok(generatedCode);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }*/



/*    @PutMapping("/reset")
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
    }*/

}


