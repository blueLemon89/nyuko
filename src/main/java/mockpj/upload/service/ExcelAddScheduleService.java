package mockpj.upload.service;

import mockpj.upload.model.Account;
import mockpj.upload.model.Campaign;
import mockpj.upload.model.Schedule;
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
public class ExcelAddScheduleService {
    @Autowired
    AccountService accountService;

    @Autowired
    CampaignService campaignService;



    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    //    static String[] HEADERs = { "acount_id", "campaign_id", "campaign_status ", "delivery_change_from","delivery_change_to" };
    static String SHEET = "Schedule";

    public  boolean hasExcelXLSXFormat(MultipartFile file) { // check dinh dang file name xlsx
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<Schedule> excelToSchedules(InputStream is) { // import file
        try {
            Workbook workbook = new XSSFWorkbook(is); // dai dien cho file, dinh dang file XLSX
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Schedule> scheduleList = new ArrayList<Schedule>();

            int rowNumber = 0;
            while (rows.hasNext()) { // neu rows co nhieu phan tu tra ve true
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Schedule schedule = new Schedule();

                int cellIdx = 0;
                while (cellsInRow.hasNext() == true) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            Long nummmm = (long) currentCell.getNumericCellValue();
                            Optional<Account> accountUpload = accountService.findAccountById(nummmm);
                            boolean account_tontai = accountUpload.isPresent();
                            if(account_tontai==true){
                                schedule.setAccount(accountUpload.get());
                            }
                            break;
                        case 1:
                            Long nummm =(long) currentCell.getNumericCellValue();
                            Optional<Campaign> campaignUpload = campaignService.findCampaignById(nummm);
                            if(campaignUpload.isPresent()){
                                schedule.setCampaign(campaignUpload.get());
                            }
                            break;
                        case 2:
                            schedule.setCampaign_status(currentCell.getStringCellValue());
                            break;
                        case 3:
                            String delivery_from = (currentCell.getStringCellValue());
                            schedule.setDelivery_change_from(Timestamp.valueOf(delivery_from));
                            break;
                        case 4:
                            String delivery_to = (currentCell.getStringCellValue());
                            schedule.setDelivery_change_to(Timestamp.valueOf(delivery_to));
                        default:
                            break;
                    }
                    cellIdx++;
                }
                if(schedule.getCampaign() == null
                        && schedule.getAccount() == null
                        && schedule.getCampaign_status() == null
                        && schedule.getDelivery_change_from() == null
                        && schedule.getDelivery_change_to()==null){
                    break;
                }
                scheduleList.add(schedule);
            }
            workbook.close();
            return scheduleList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
