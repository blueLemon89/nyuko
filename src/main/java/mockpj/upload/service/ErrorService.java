package mockpj.upload.service;

import mockpj.upload.model.Error;
import mockpj.upload.repository.ErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ErrorService {
    @Autowired
    ErrorRepository errorRepository;

    public Optional<Error> findErrorById(Integer id){
        return errorRepository.findById(id);
    }
}
