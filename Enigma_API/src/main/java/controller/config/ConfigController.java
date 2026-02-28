package controller.config;

import dto.DtoMachineSpecification;
import dto.ManualConfigurationModel;
import engine.Engine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import patmal.course.enigma.api.model.EnigmaManualConfigRequest;
import patmal.course.enigma.service.ConfigurationService;
import patmal.course.enigma.session.SessionManager;
import patmal.course.enigma.api.model.GetCurrentMachineStatus200Response; // main response model for the endpoint(interface)
import patmal.course.enigma.api.model.GetCurrentMachineStatus200ResponseOneOf; // for verbose=true
import patmal.course.enigma.api.model.GetCurrentMachineStatus200ResponseOneOf1; // for verbose=false




@RequiredArgsConstructor
@RestController
@RequestMapping("/enigma/config")
public class ConfigController {
    //private SessionManager sessionManager;
    private final ConfigurationService configurationService;
    private final WebToManualConfigurationRequestConverter manualConverter;
    private final MachineDetailsResponseToWebConverter detailsConverter;




    @GetMapping()
    public ResponseEntity<GetCurrentMachineStatus200Response> getCurrentMachineDetails(@RequestParam("sessionID") String sessionID,
                                                                                            @RequestParam("verbose") boolean verbose) {
        if(!verbose){
            DtoMachineSpecification compactSpec = configurationService.GetCompactCurrentMachineDetailsService(sessionID);
            String CurrentRotorsPositionCompact=configurationService.getRotorsPos(sessionID);
            GetCurrentMachineStatus200ResponseOneOf1 compactResponse = detailsConverter.convert(compactSpec,CurrentRotorsPositionCompact);
            return ResponseEntity.ok(compactResponse);
        }

        else{
            //Engine engine = sessionManager.getEngineBySessionId(sessionID);


            //DtoMachineSpecification spec = engine.showMachineDetails();
            GetCurrentMachineStatus200ResponseOneOf response = detailsConverter.convertVerbose(spec);
            return ResponseEntity.ok(response);
        }



    }



    @PutMapping("/manual")
    public ResponseEntity<String> setManualCodeSelection(@RequestBody EnigmaManualConfigRequest request) {
        ManualConfigurationModel model =
                manualConverter.convert(request);
        try {
            configurationService.CodeManualService(request.getSessionID(), model);
            return ResponseEntity.ok("Manual configuration applied successfully");
        } catch (
                Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }



   @PutMapping("/automatic")
    public ResponseEntity<String>
    setAutomaticCodeSetup(@RequestParam("sessionID") String sessionID) {
        try {
             String generatedCode=configurationService.CodeAutoService(sessionID);
            return ResponseEntity.ok(generatedCode);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reset")
    public ResponseEntity<String>
    resetToOriginalCode(@RequestParam("sessionID") String sessionID) {
        try {
            DtoMachineSpecification spec=configurationService.ResetCodeService(sessionID);
            return ResponseEntity.ok(spec.getOriginalCode());

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}


