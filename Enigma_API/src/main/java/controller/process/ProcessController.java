package controller.process;

import dto.DtoMachineSpecification;
import dto.ProcessInputModel;
import dto.ProcessInputResult;
import engine.Engine;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patmal.course.enigma.api.model.ProcessInput200Response;


@RequiredArgsConstructor
@RestController
@RequestMapping("/enigma/process")
public class ProcessController {

    private final Engine engine;
    private final WebToProcessInputRequestConverter requestConverter;
    private final ProcessInputResponseToWebConverter responseConverter;
/*
    @PostMapping(produces = "application/json")
    public ResponseEntity<ProcessInput200Response> processInput(
            @RequestParam("input") String input,
            @RequestParam("sessionID") String sessionID // כרגע לא בשימוש
    ) {
        try {

            // 1️⃣ Web → Internal Command
            ProcessInputModel model =
                    requestConverter.convert(input);

            // 2️⃣ Engine processing
            String output = engine.processMessage(model.getInput());

            // 3️⃣ Get current rotor state
            DtoMachineSpecification spec =
                    engine.showMachineDetails();

            ProcessInputResult result =
                    new ProcessInputResult(
                            output,
                            spec.getCurrentCode()
                    );

            // 4️⃣ Internal → Web
            ProcessInput200Response response =
                    responseConverter.convert(result);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
*/



    @PostMapping
    public ResponseEntity<ProcessInput200Response> processInput(
            @RequestParam("input") String input,
            @RequestParam("sessionId") String sessionId
    ) {
        try {

            String output = engine.processMessage(input);

            String RotorState = engine.getCurrentRotorPositions();

            ProcessInput200Response response =
                    new ProcessInput200Response()
                            .output(output)
                            .currentRotorsPositionCompact(
                                    RotorState);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ProcessInput200Response()
                    .output("Error processing input: " + e.getMessage())
                    .currentRotorsPositionCompact(""));
        }
    }


}
