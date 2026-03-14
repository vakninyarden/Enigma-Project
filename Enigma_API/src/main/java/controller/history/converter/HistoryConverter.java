package controller.history.converter;


import dto.ProcessRecord;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.HistoryEntry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HistoryConverter {

    private HistoryEntry convertToHistoryEntry(ProcessRecord record) {
        return new HistoryEntry(
                record.getSorceMessage(),
                record.getProcessedMessage(),
                (int) record.getTimeInNanos()
        );
    }

    public Map<String, List<HistoryEntry>> convertToHistoryEntryMap(Map<String, List<ProcessRecord>> history) {
        return history.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::convertToHistoryEntry)
                                .collect(Collectors.toList())
                ));
    }


}
