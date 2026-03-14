
package controller.history;

import controller.history.converter.HistoryConverter;
import dto.ProcessRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import patmal.course.enigma.api.model.GetMachineHistory400Response;
import patmal.course.enigma.service.HistoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("enigma/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final HistoryConverter historyConverter;

    @GetMapping
    public ResponseEntity<?> getHistory(
            @RequestParam(value = "sessionID", required = false) String sessionID,
            @RequestParam(value = "machineName", required = false) String machineName) {
        Map<String, List<ProcessRecord>> history;

        try{
            history = historyService.getHistory(sessionID, machineName);
            return ResponseEntity.ok(historyConverter.convertToHistoryEntryMap(history));
        }
            catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(
                        new GetMachineHistory400Response().error(e.getMessage()));
            }


    }
}

