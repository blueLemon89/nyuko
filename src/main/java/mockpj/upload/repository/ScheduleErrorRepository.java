package mockpj.upload.repository;

import mockpj.upload.model.ScheduleError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScheduleErrorRepository extends JpaRepository<ScheduleError,Integer> {
//    @Query(value = "SELECT * FROM ScheduleError se where se.file.file_id =:id ",nativeQuery = true)
    @Query(value = "SELECT * FROM ScheduleError se  WHERE se.file_id =:id",nativeQuery = true)
    List<ScheduleError> getAllByFileId(Integer id);

//    @Query(value = "SELECT * FROM scheduleerror  WHERE ORDER BY schedule_error_id LIMIT 1",nativeQuery = true)
//    List<ScheduleError> getAllByLastFileImport();
}
