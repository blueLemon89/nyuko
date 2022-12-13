package mockpj.upload.service;

import mockpj.upload.excelhelper.ExcelHelperWriteFile;
import mockpj.upload.model.ScheduleError;
import mockpj.upload.repository.ScheduleErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleErrorService {
    @Autowired
    ScheduleErrorRepository scheduleErrorRepository;

    public Optional<ScheduleError> findScheduleErrorById(Integer id){
        return scheduleErrorRepository.findById(id);
    }

    public List<ScheduleError>  getListScheduleErrorByFileId(Integer id){
        List<ScheduleError> scheduleErrorList = scheduleErrorRepository.getAllByFileId(id);
        return scheduleErrorList;
    }

    public ByteArrayInputStream exportFile(List<ScheduleError> scheduleErrorList){
        ExcelHelperWriteFile helperWriteFile = new ExcelHelperWriteFile();
        ByteArrayInputStream inputStream = helperWriteFile.writeScheduleErrorsToFileExcel(scheduleErrorList);
        return inputStream;
    }

    public List<ScheduleError> getAllScheduleError(){
        return scheduleErrorRepository.findAll();
    }

}
