package controller.process;

import dto.ProcessInputResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patmal.course.enigma.api.model.ProcessInput200Response;
import patmal.course.enigma.service.ProcessService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/enigma/process")
public class ProcessController {
    private final ProcessService processService;
    private final ProcessInputResponseToWebConverter responseConverter;

    @PostMapping
    public ResponseEntity<ProcessInput200Response> processInput(
            @RequestParam("input") String input,
            @RequestParam("sessionId") String sessionId
    ) {
        try {

            ProcessInputResult serviceModel = processService.processInput(input, sessionId);
            ProcessInput200Response response = responseConverter.convert(serviceModel);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(new ProcessInput200Response()
                    .output("Error processing input: " + e.getMessage())
                    .currentRotorsPositionCompact(""));
        }
    }


}
