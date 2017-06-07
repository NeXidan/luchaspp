package org.bsuir.labs.spp.service.documents.services;

import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.bsuir.labs.spp.domain.User;
import org.bsuir.labs.spp.service.documents.BaseDocumentService;
import org.bsuir.labs.spp.service.documents.dto.UserStatisticDTO;
import org.bsuir.labs.spp.service.documents.interfaces.UserDocumentService;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserDocumentServiceImpl extends BaseDocumentService implements UserDocumentService {
    @Override
    public void generateCsv(List<UserStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, CSV_FILE_FORMAT);
        setResponseContentType(response, CSV_CONTENT_TYPE);

        try {
            ICsvListWriter listWriter = new CsvListWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

            listWriter.writeHeader("Id", "Full Name", "Open", "In progress", "Closed");

            for (UserStatisticDTO userData: data) {
                listWriter.write(userData.getId(), userData.getFullName(), userData.getOpenTasks(), userData.getProgressTasks(), userData.getCompletedTasks());
            }

            listWriter.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void generatePdf(List<UserStatisticDTO> data, boolean lock, HttpServletResponse response) {
        try {
            PdfWriter writer = preparePdfGenerating("Users statistics", response, lock, "Id", "Full Name", "Open", "In progress", "Closed");

            for (UserStatisticDTO usersData: data) {
                this.addCell(String.valueOf(usersData.getId()));
                this.addCell(usersData.getFullName());
                this.addCell(String.valueOf(usersData.getOpenTasks()));
                this.addCell(String.valueOf(usersData.getProgressTasks()));
                this.addCell(String.valueOf(usersData.getCompletedTasks()));
            }

            finishPdfGenerating();
            writer.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateXls(List<UserStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, XLS_FILE_FORMAT);
        setResponseContentType(response, XLS_CONTENT_TYPE);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Users statistics");

        sheet.setDefaultColumnWidth(15);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        sheet.setColumnWidth(0, 256 * 4);
        sheet.setColumnWidth(1, 256 * 30);
        sheet.setColumnWidth(2, 256 * 6);
        sheet.setColumnWidth(3, 256 * 11);
        sheet.setColumnWidth(4, 256 * 7);

        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Users statistics");

        HSSFRow header = sheet.createRow(1);

        setXlsHeaders(header, style, "Id", "Full Name", "Open", "In progress", "Closed");

        int rowCount = 2;

        for (UserStatisticDTO usersData: data) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(usersData.getId());
            aRow.createCell(1).setCellValue(usersData.getFullName());
            aRow.createCell(2).setCellValue(usersData.getOpenTasks());
            aRow.createCell(3).setCellValue(usersData.getProgressTasks());
            aRow.createCell(4).setCellValue(usersData.getCompletedTasks());
        }

        try {
            workbook.write(response.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
