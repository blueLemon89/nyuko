package mockpj.upload.controller;

import mockpj.upload.dto.ScheduleDTO;
import mockpj.upload.model.Account;
import mockpj.upload.model.File;
import mockpj.upload.model.Schedule;
import mockpj.upload.model.ScheduleError;
import mockpj.upload.repository.AccountRepository;
import mockpj.upload.service.ExcelAddScheduleService;
import mockpj.upload.service.FileService;
import mockpj.upload.service.ScheduleErrorService;
import mockpj.upload.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8090")
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ScheduleErrorService scheduleErrorService;

    @Autowired
    ExcelAddScheduleService excelAddScheduleService;

    @Autowired
    FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<List<ScheduleError>> importScheduleByExcel(@RequestParam(value = "file") MultipartFile file){
        String file_name = file.getOriginalFilename();
        boolean hasXLSX = excelAddScheduleService.hasExcelXLSXFormat(file);
        if(hasXLSX && file_name.length() <= 250){
//            scheduleService.saveScheduleByfileExcel(file);
            List<ScheduleError> scheduleErrorList = scheduleService.saveScheduleByfileExcel(file);
            return new ResponseEntity<>(scheduleErrorList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

     @GetMapping("/export")
     public ResponseEntity<Resource> exportFileError(){
         Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String fileName = "ScheduleErrors" + "-"+ now +".xlsx";
        Optional<File> file = fileService.findLastFileInport();
        List<ScheduleError> scheduleErrorList = scheduleErrorService.getListScheduleErrorByFileId(file.get().getFile_id());
        InputStreamResource inputStreamSource = new InputStreamResource(scheduleErrorService.exportFile(scheduleErrorList));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + fileName)
                .contentType(MediaType.valueOf("application/vnd.ms-excel"))
                .body(inputStreamSource);
     }

     @GetMapping("/{id}")
     public ResponseEntity<Account> getSchedule(@PathVariable Long id){
         Optional<Account> account = accountRepository.findById(id);
         if(account.isPresent()){
             return new ResponseEntity<>(account.get(),HttpStatus.OK) ;
         }
         return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }

     @GetMapping("/file/{id}")
     public  List<ScheduleError> getListScheduleErrorListByFileId(@PathVariable Integer id){
        List<ScheduleError> scheduleErrorList = scheduleErrorService.getListScheduleErrorByFileId(id);
        return scheduleErrorList;
     }


     @GetMapping("/listall")
    public ResponseEntity<List<Schedule>>  getAllSchedule(){
        List<Schedule> scheduleList = scheduleService.findAllSchedule();

        return new ResponseEntity<>(scheduleList,HttpStatus.OK);
     }

     @GetMapping("/listdto")
     public ResponseEntity<List<ScheduleDTO>> getAllScheduleDTO(){
        List<ScheduleDTO> scheduleDTOList = scheduleService.findAllScheDuleDTO();
        return new ResponseEntity<>(scheduleDTOList,HttpStatus.OK);
     }

    @GetMapping("/scheduleerrors")
    public ResponseEntity<List<ScheduleError>> getAllScheduleError(){
        List<ScheduleError> scheduleErrorList = scheduleErrorService.getAllScheduleError();
        return new ResponseEntity<>(scheduleErrorList, HttpStatus.OK);
    }

}
