/*
package controller.history.converter;
import engine.statistic.StatisticsManager;
import org.springframework.stereotype.Component;
import patmal.course.enigma.api.model.*;



import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistoryMapper {

    public List<HistoryEntry> toDtoList(StatisticsManager stats) {

        return stats.getStatisticsData()
                .map(record -> {
                    HistoryEntry entry = new HistoryEntry();
                    entry.setInput(record.getInput());
                    entry.setOutput(record.getOutput());
                    entry.setTime(record.getTime());
                    return entry;
                })
                .collect(Collectors.toList());
    }
}*/
