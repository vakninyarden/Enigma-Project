package patmal.course.enigma.service;

import dto.ProcessRecord;
import org.springframework.stereotype.Service;
import patmal.course.enigma.PersistanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class HistoryService {
    private final PersistanceService persistanceService;

    public HistoryService(PersistanceService persistanceService) {
        this.persistanceService = persistanceService;
    }

    public Map<String, List<ProcessRecord>> getHistoryBySessionId(String sessionId) {
        List<ProcessRecord> records = persistanceService.getHistoryBySessionId(sessionId);
        Map<String, List<ProcessRecord>> history = new HashMap<>();
        for (ProcessRecord record : records) {
            history.computeIfAbsent(record.getCurrentCode(), k -> new ArrayList<>()).add(record);
        }
        return history;
    }

    public Map<String, List<ProcessRecord>> getHistoryByMachineName(String machineName) {
        List<ProcessRecord> records = persistanceService.getHistoryByMachineName(machineName);
        Map<String, List<ProcessRecord>> history = new HashMap<>();
        for (ProcessRecord record : records) {
            history.computeIfAbsent(record.getCurrentCode(), k -> new ArrayList<>()).add(record);
        }
        return history;
    }

    }



