package controller.loader;

import dto.load.LoadMachineCommand;
import dto.load.LoadMachineResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import patmal.course.enigma.LoadService;
import patmal.course.enigma.api.model.LoadMachineFromXml200Response;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enigma")
public class LoaderController {

    private final LoadService LoadService;
    private final WebToLoadMachineRequestConverter webToLoadMachineRequestConverter;


    @PostMapping("/load")
    public ResponseEntity<LoadMachineFromXml200Response> loadMachineFromXml(
            @RequestPart("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new LoadMachineFromXml200Response()
                            .success(false)
                            .error("File not provided")
                            .name(null)
            );
        }
        try {
            LoadMachineCommand command =
                    webToLoadMachineRequestConverter.convert(file);

            LoadMachineResult result = LoadService.loadMachine(command);

            return ResponseEntity.ok
                    (new LoadMachineFromXml200Response().
                            success(true)
                            .name(result.getMachineName())
                            .error(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new LoadMachineFromXml200Response()
                            .success(false)
                            .name(null)
                            .error((e.getMessage()))
            );
        }
    }
}



