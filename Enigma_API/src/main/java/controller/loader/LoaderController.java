
package controller.loader;

import controller.loader.converter.LoaderResponseToDtoMapper;
import engine.Engine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import dto.response.loader.EnigmaMachineWeb;
import patmal.course.enigma.api.model.LoadMachineFromXml200Response;

import java.io.InputStream;

/*
@RestController
@RequestMapping("/enigma") //the url for the controller
public class LoaderController {
    private final Engine engine;
    private final EnigmaMachineToWebConverter enigmaMachineToWebConverter;

    @Autowired
    public LoaderController(Engine engine, EnigmaMachineToWebConverter enigmaMachineToWebConverter) {
        this.enigmaMachineToWebConverter = enigmaMachineToWebConverter;
        this.engine = engine;
    }

    @PostMapping("/load")
    public ResponseEntity<EnigmaMachineWeb> loadEnigmaMachine(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || file == null) {
            EnigmaMachineWeb emptyFileResponse =
                    enigmaMachineToWebConverter.createFailedResponse("No file was provided in the request");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyFileResponse);
        }

        try {
            InputStream inputStream = file.getInputStream();
            engine.loadXml(inputStream);


            //TO DO : ADD A FUNCTION THAT EXTRACT THE NAME OF THE MACHINE
            // FROM THE ENGINE (OR THE LOAD MANAGER) AND PASS IT TO THE WEB RESPONSE
            //String enigmaMachineName = engine.getMachineName();


            EnigmaMachineWeb successResponse =
                    enigmaMachineToWebConverter.createSuccessfullResponse("TO DO - EXTRACT");
            return ResponseEntity.ok(successResponse);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Failed to load Enigma machine: " + e.getMessage();
            EnigmaMachineWeb errorResponse = enigmaMachineToWebConverter.createFailedResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }
}*/




@RestController
@RequestMapping("/load")
@RequiredArgsConstructor
public class LoaderController {

    private final Engine engine;
    private final LoaderResponseToDtoMapper responseMapper;

    @PostMapping
    public ResponseEntity<LoadMachineFromXml200Response> load(
            @RequestBody LoadMachineFromXmlRequest request) {
        // צריך לממש עם הטעינת XML שהוספנו במכונה

        String xml = requestMapper.extractXml(request);
        engine.loadMachine(xml);

        return ResponseEntity.ok(responseMapper.success());
    }
}