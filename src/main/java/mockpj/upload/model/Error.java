package mockpj.upload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "error")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer error_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_error_id",referencedColumnName = "schedule_error_id")
    private ScheduleError schedule_error;

    @NotNull
    private String error_code;

    @NotNull
    private String name_error;
}
