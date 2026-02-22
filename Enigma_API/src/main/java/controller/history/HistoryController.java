/*
package controller.history;

import controller.history.converter.HistoryMapper;
import engine.Engine;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import patmal.course.enigma.api.model.HistoryEntry;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final Engine engine;
    private final HistoryMapper responseMapper;

    @GetMapping
    public ResponseEntity<List<HistoryEntry>> history() {
        return ResponseEntity.ok(
                responseMapper.toDtoList(engine.getStatisticsData())
        );
    }
}
*/
