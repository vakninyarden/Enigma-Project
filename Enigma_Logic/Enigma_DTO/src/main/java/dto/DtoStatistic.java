package dto;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DtoStatistic {
    private Map<String, List<ProcessRecord>> statisticsData;

    public DtoStatistic(Map<String, List<ProcessRecord>> statisticsData) {
        this.statisticsData = statisticsData;
    }

}

