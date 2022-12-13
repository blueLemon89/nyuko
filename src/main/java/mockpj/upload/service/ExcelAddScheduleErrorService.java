package mockpj.upload.service;

import mockpj.upload.model.Account;
import mockpj.upload.model.Campaign;
import mockpj.upload.model.ScheduleError;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelAddScheduleErrorService {
    @Autowired
    AccountService accountService;
    @Autowired
    CampaignService campaignService;
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    //    static String[] HEADERs = { "acount_id", "campaign_id", "campaign_status ", "delivery_change_from","delivery_change_to" };
    static String SHEET = "Schedule";

    public  boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<ScheduleError> excelToSchedulesError(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is); // dinh dang file XLSX
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<ScheduleError> scheduleErrorList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                ScheduleError scheduleError = new ScheduleError();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            Long nummmm = (long) currentCell.getNumericCellValue();
                            Optional<Account> accountUpload = accountService.findAccountById(nummmm);
                            if(accountUpload.isPresent()){
                                scheduleError.setAccount(accountUpload.get());
                            }
                            break;
                        case 1:
                            Long nummm =(long) currentCell.getNumericCellValue();
                            Optional<Campaign> campaignUpload  = campaignService.findCampaignById(nummm);
                            if(campaignUpload.isPresent()){
                                scheduleError.setCampaign(campaignUpload.get());
                            }
                            break;
                        case 2:
                            scheduleError.setCampaign_status(currentCell.getStringCellValue());
                            break;
                        case 3:
                            scheduleError.setDelivery_change_from(Timestamp.valueOf(currentCell.getStringCellValue()));
                            break;
                        case 4:
                            scheduleError.setDelivery_change_to((Timestamp.valueOf(currentCell.getStringCellValue())));
                        default:
                            break;
                    }
                    cellIdx++;
                }
                if(scheduleError.getCampaign() == null
                        && scheduleError.getAccount() == null
                        && scheduleError.getCampaign_status() == null
                        && scheduleError.getDelivery_change_from() == null
                        && scheduleError.getDelivery_change_to()==null){
                    break;
                }
                scheduleErrorList.add(scheduleError);
            }
            workbook.close();
            return scheduleErrorList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
