package controller.process;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patmal.course.enigma.api.model.ProcessInput200Response;
import engine.Engine;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class ProcessController {

    private final Engine engine;
    private final ProcessRequestMapper requestMapper;
    private final ProcessResponseMapper responseMapper;

    @PostMapping
    public ResponseEntity<ProcessInput200Response> process(
            @RequestBody ProcessInputRequest request) {

        // 1️⃣ DTO → Engine
        String input = requestMapper.toEngineInput(request);

        // 2️⃣ Business logic
        String result = engine.process(input);

        // 3️⃣ Engine → DTO
        ProcessInput200Response response =
                responseMapper.toDto(result);

        return ResponseEntity.ok(response);
    }
}