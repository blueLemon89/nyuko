package mockpj.upload.repository;

import mockpj.upload.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File,Integer> {
    @Query(value = "SELECT * FROM file ORDER BY file_id DESC LIMIT 1",nativeQuery = true)
    public Optional<File>  getLastFileImport();
}
