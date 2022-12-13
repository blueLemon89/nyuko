package mockpj.upload.repository;

import mockpj.upload.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    @Query(value = "SELECT * FROM schedule s WHERE " +
            "delivery_change_from <= now() && " +
            "delivery_change_to >= now() && " +
            "action_done = false limit 100",nativeQuery = true)
    List<Schedule> find100ScheduleWhereNowBetweenFromTo();

}
