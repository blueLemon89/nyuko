package mockpj.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockpj.upload.model.Schedule;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

    private Integer id;

    private Long account_id;

    private Integer file_id;

    private Long campaign_id;

    private String campaign_status;

    private String delivery_change_from;

    private String delivery_change_to;

    private boolean action_done;

    public ScheduleDTO toDTO(Schedule schedule){
        ScheduleDTO scheduleDTO =  new ScheduleDTO();
        scheduleDTO.id = schedule.getId();
        scheduleDTO.account_id = schedule.getAccount().getAccount_id();
        scheduleDTO.campaign_id = schedule.getCampaign().getCampaign_id();
        scheduleDTO.file_id = schedule.getFile().getFile_id();
        scheduleDTO.campaign_status = schedule.getCampaign_status();
        scheduleDTO.delivery_change_from = (schedule.getDelivery_change_from()).toString();
        scheduleDTO.delivery_change_to = schedule.getDelivery_change_to().toString();
        scheduleDTO.action_done = schedule.isAction_done();
        return scheduleDTO;
    }

}
