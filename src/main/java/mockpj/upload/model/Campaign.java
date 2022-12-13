package mockpj.upload.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name="campaign")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Size(max = 10,message = "Campaign ID not allowed to be more than 10")
    private Long campaign_id;

    @NotNull
    @Size(max = 25,message = "Campaign ID not allowed to be more than 25")
    private String campaign_name;

    @NotNull
    private String campaign_status;

    @NotNull
    private Timestamp delivery_change_from;

    @NotNull
    private Timestamp delivery_change_to;

    public boolean correctCampaignStatus(String campaign_tus){
        if(campaign_tus.equals("Active")|| campaign_tus.equals("Pause") || campaign_tus.equals("Remove"))
            return true;
        else return false;
    }


}
