package mockpj.upload.service;

import mockpj.upload.dto.ScheduleDTO;
import mockpj.upload.model.*;
import mockpj.upload.model.Error;
import mockpj.upload.repository.ErrorRepository;
import mockpj.upload.repository.FileRepository;
import mockpj.upload.repository.ScheduleErrorRepository;
import mockpj.upload.repository.ScheduleRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleErrorRepository scheduleErrorRepository;

    @Autowired
    ErrorRepository errorRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    ExcelAddScheduleService excelAddScheduleService;

    @Autowired
    ExcelAddScheduleErrorService excelAddScheduleErrorService;

    public void saveSchedule(Schedule schedule){
        scheduleRepository.save(schedule);
    }

    /**
     *
     * @param file
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public List<ScheduleError> saveScheduleByfileExcel(MultipartFile file){
        List<ScheduleError> scheduleErrorListExport = new ArrayList<>();
        try {
            List<Schedule> scheduleList = excelAddScheduleService.excelToSchedules(file.getInputStream());
            List<ScheduleError> scheduleErrorList = excelAddScheduleErrorService.excelToSchedulesError(file.getInputStream());
            File fileDB = new File();
            fileDB.setFile_name(file.getOriginalFilename());
            fileRepository.save(fileDB);

            for(Schedule schedule : scheduleList){
                String campaign_tus = schedule.getCampaign_status();
                boolean is_true = correctDeliveryChangeFrom_To(schedule.getDelivery_change_from(),schedule.getDelivery_change_to()) == true;
                 if(correctCampaignStatus(campaign_tus) == true && is_true ){
                     schedule.setFile(fileDB);
                     schedule.setAction_done(false);
                    saveSchedule(schedule);
                }
            }
            for(ScheduleError scheduleError: scheduleErrorList){
                Integer length_campaign_id = lengthOfCampaignId(scheduleError.getCampaign().getCampaign_id());
                boolean correct_status = correctCampaignStatus(scheduleError.getCampaign_status());
                boolean from_before_to = scheduleError.getDelivery_change_from().compareTo(scheduleError.getDelivery_change_to()) > 0;
                boolean from_before_now =  scheduleError.getDelivery_change_from().before(Timestamp.valueOf(LocalDateTime.now()));
                boolean to_before_now =  scheduleError.getDelivery_change_to().before(Timestamp.valueOf(LocalDateTime.now()));

               if(length_campaign_id > 10|| correct_status == false||from_before_to ||from_before_now ||to_before_now){
                   scheduleError.setFile(fileDB);
                   scheduleErrorRepository.save(scheduleError);
                   if(lengthOfCampaignId(scheduleError.getCampaign().getCampaign_id()) > 10){
                       Error error = new Error();
                       error.setSchedule_error(scheduleError);
                       error.setError_code("CAMPAIGN_ID_ERROR_01");
                       error.setName_error("CAMPAIGN_ID is too 10 characters");
                       errorRepository.save(error);
                   }

                   if(correctCampaignStatus(scheduleError.getCampaign_status()) == false){
                       Error error = new Error();
                       error.setSchedule_error(scheduleError);
                       error.setError_code("STATUS_ERROR_01");
                       error.setName_error("Status not in Active,Pause,Remove");
                       errorRepository.save(error);
                   }
                   if(scheduleError.getDelivery_change_from().compareTo(scheduleError.getDelivery_change_to()) > 0){
                       Error error = new Error();
                       error.setSchedule_error(scheduleError);
                       error.setError_code("DELIVERY_FROM_ERROR_01");
                       error.setName_error("DELIVERY_FROM is after DELIVERY_TO");
                       errorRepository.save(error);
                   }
                   if(scheduleError.getDelivery_change_from().before(Timestamp.valueOf(LocalDateTime.now()))){
                       Error error = new Error();
                       error.setSchedule_error(scheduleError);
                       error.setError_code("DELIVERY_FROM_ERROR_02");
                       error.setName_error("DELIVERY_FROM is after CURRENT TIME");
                       errorRepository.save(error);
                   }
                   if(scheduleError.getDelivery_change_to().before(Timestamp.valueOf(LocalDateTime.now()))){
                       Error error = new Error();
                       error.setSchedule_error(scheduleError);
                       error.setError_code("DELIVERY_TO_ERROR_01");
                       error.setName_error("DELIVERY_TO is after CURRENT TIME");
                       errorRepository.save(error);
                   }
               }
            List<ScheduleError> errorList = scheduleErrorRepository.getAllByFileId(fileDB.getFile_id());
                scheduleErrorListExport = errorList;

            }
        } catch (IOException e) {
            throw new RuntimeException("Fail" + e.getMessage());
        }
        return scheduleErrorListExport;
    }

    private boolean correctCampaignStatus(String campaign_tus){
        if(campaign_tus.equals("Active")|| campaign_tus.equals("Pause") || campaign_tus.equals("Remove"))
            return true;
        else return false;
    }

    private boolean correctDeliveryChangeFrom_To(Timestamp from, Timestamp to){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        if(to.compareTo(from) > 0 && to.compareTo(now) > 0  && from.compareTo(now) >0 )
            return true;
        else return false;
    }

    public List<Schedule> findAllSchedule(){
        return scheduleRepository.findAll();
    }

    public List<ScheduleDTO> findAllScheDuleDTO(){
        List<Schedule> scheduleList = scheduleRepository.findAll();
        ScheduleDTO dto = new ScheduleDTO();
        List<ScheduleDTO> scheduleDTOS = scheduleList.stream().map(item ->dto.toDTO(item)).collect(Collectors.toList());
        return scheduleDTOS;
    }

    private int lengthOfCampaignId(Long id){
        String number = Long.toString(id);
        return number.length();
    }

    public List<Schedule> get100ScheduleToAction(){
        return scheduleRepository.find100ScheduleWhereNowBetweenFromTo();
    }

}
