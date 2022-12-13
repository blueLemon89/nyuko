package mockpj.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleErrorDTO {
    private Integer schedule_error_id;

    private Integer file_id;

    private Integer account_id;

    private Integer campaign_id;

    private String campaign_status;

    private Timestamp delivery_change_from;

    private Timestamp delivery_change_to;

//    public ScheduleErrorDTO

}
