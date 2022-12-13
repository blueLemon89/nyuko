package mockpj.upload.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name="scheduleerror")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schedule_error_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id",referencedColumnName = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id",referencedColumnName = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "campaign_id",referencedColumnName = "campaign_id")
    private Campaign campaign;

    @NotNull
    private String campaign_status;

    @NotNull
    private Timestamp delivery_change_from;

    @NotNull
    private Timestamp delivery_change_to;
}
