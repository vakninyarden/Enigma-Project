package dto;

//import engine.statistic.ProcessRecord;

import java.util.List;
import java.util.Map;

public class DtoStatistic {
    private Map<String, List<ProcessRecord>> statisticsData;

    public DtoStatistic(Map<String, List<ProcessRecord>> statisticsData) {
        this.statisticsData = statisticsData;
    }

    public Map<String, List<ProcessRecord>> getStatisticsData() {
        return statisticsData;
    }
}

