package dto.response.config;
import  lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ManualConfigToWeb {
    private boolean success;
    private String FeedbackMessage;
}
