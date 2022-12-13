package mockpj.upload.excelhelper;

import mockpj.upload.model.ScheduleError;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelperWriteFile {
    static String[] HEADERs = { "account_id", "campaign_id", "campaign_status", "delivery_change_from","delivery_change_to" };
    static String SHEET = "Schedules Error";



    public ByteArrayInputStream writeScheduleErrorsToFileExcel(List<ScheduleError> scheduleErrorList){
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet(SHEET);

            Row row_header = sheet.createRow(0);
            for(int index_cell = 0; index_cell < HEADERs.length;index_cell++){
                Cell cell = row_header.createCell(index_cell);
                cell.setCellValue(HEADERs[index_cell]);
            }

            int index_row = 1;
            for(ScheduleError scheduleError : scheduleErrorList){

                Row row = sheet.createRow(index_row);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(scheduleError.getAccount().getAccount_id());

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(scheduleError.getCampaign().getCampaign_id());

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(scheduleError.getCampaign_status());

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(scheduleError.getDelivery_change_from().toString());

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(scheduleError.getDelivery_change_to().toString());

                index_row++;
            }

            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        }catch (IOException e){
            throw new RuntimeException("fail"+e);
        }

    }
}
