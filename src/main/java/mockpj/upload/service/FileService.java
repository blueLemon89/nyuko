package mockpj.upload.service;

import mockpj.upload.model.File;
import mockpj.upload.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    public Optional<File> findFileById(Integer id){
        return fileRepository.findById(id);
    }

    public Optional<File> findLastFileInport(){
        return fileRepository.getLastFileImport();
    }
}
