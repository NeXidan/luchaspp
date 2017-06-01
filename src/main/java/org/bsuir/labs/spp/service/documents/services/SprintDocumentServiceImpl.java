package org.bsuir.labs.spp.service.documents.services;

import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.bsuir.labs.spp.service.documents.BaseDocumentService;
import org.bsuir.labs.spp.service.documents.dto.SprintStatisticDTO;
import org.bsuir.labs.spp.service.documents.interfaces.SprintDocumentService;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SprintDocumentServiceImpl extends BaseDocumentService implements SprintDocumentService {
    @Override
    public void generateCsv(List<SprintStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, CSV_FILE_FORMAT);
        setResponseContentType(response, CSV_CONTENT_TYPE);

        try {
            ICsvListWriter listWriter = new CsvListWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

            listWriter.writeHeader("Id", "Name", "Project", "From", "To", "Tasks");

            for (SprintStatisticDTO sprintData: data) {
                listWriter.write(sprintData.getId(), sprintData.getName(), sprintData.getProject(),
                    sprintData.getFromDate(), sprintData.getToDate(), sprintData.getTasks());
            }

            listWriter.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void generatePdf(List<SprintStatisticDTO> data, boolean lock, HttpServletResponse response) {
        try {
            PdfWriter writer = preparePdfGenerating("Sprints statistics", response, lock, "Id", "Name", "Project", "From", "To", "Tasks");

            for (SprintStatisticDTO sprintData: data) {
                this.pdfTable.addCell(String.valueOf(sprintData.getId()));
                this.pdfTable.addCell(sprintData.getName());
                this.pdfTable.addCell(sprintData.getProject());
                this.pdfTable.addCell(String.valueOf(sprintData.getFromDate()));
                this.pdfTable.addCell(String.valueOf(sprintData.getToDate()));
                this.pdfTable.addCell(String.valueOf(sprintData.getTasks()));
            }

            finishPdfGenerating();
            writer.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateXls(List<SprintStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, XLS_FILE_FORMAT);
        setResponseContentType(response, XLS_CONTENT_TYPE);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Sprints statistics");

        sheet.setDefaultColumnWidth(15);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        HSSFRow header = sheet.createRow(0);

        setXlsHeaders(header, style, "Id", "Name", "Project", "From", "To", "Tasks");

        int rowCount = 1;

        for (SprintStatisticDTO usersData: data) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(usersData.getId());
            aRow.createCell(1).setCellValue(usersData.getName());
            aRow.createCell(2).setCellValue(usersData.getProject());
            aRow.createCell(3).setCellValue(String.valueOf(usersData.getFromDate()));
            aRow.createCell(4).setCellValue(String.valueOf(usersData.getToDate()));
            aRow.createCell(5).setCellValue(String.valueOf(usersData.getTasks()));
        }

        try {
            workbook.write(response.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
