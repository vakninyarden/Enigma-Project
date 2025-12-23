package engine.statistic;
import dto.ProcessRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.Serializable;

public class StatisticsManager implements Serializable {
    private Map<String, List<ProcessRecord>> statisticsData;
    private String currentCode;

    public StatisticsManager() {
        this.statisticsData = new HashMap<>();
    }

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }

    public void addStatistic(ProcessRecord message) {
        statisticsData.computeIfAbsent(currentCode, k -> new ArrayList<>()).add(message);

    }

    public Map<String, List<ProcessRecord>> getStatisticsData() {
        return statisticsData;
    }


    public void resetStatistics() {
        statisticsData.clear();
    }


}
