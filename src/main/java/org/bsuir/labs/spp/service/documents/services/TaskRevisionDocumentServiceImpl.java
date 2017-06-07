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
import org.bsuir.labs.spp.service.documents.dto.TaskRevisionStatisticDTO;
import org.bsuir.labs.spp.service.documents.interfaces.SprintDocumentService;
import org.bsuir.labs.spp.service.documents.interfaces.TaskRevisionDocumentService;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class TaskRevisionDocumentServiceImpl extends BaseDocumentService implements TaskRevisionDocumentService {
    @Override
    public void generateCsv(List<TaskRevisionStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, CSV_FILE_FORMAT);
        setResponseContentType(response, CSV_CONTENT_TYPE);

        try {
            ICsvListWriter listWriter = new CsvListWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

            listWriter.writeHeader("UpdateAt", "Name", "Description", "Status", "Priority", "Sprint", "Project", "Assignee");

            for (TaskRevisionStatisticDTO taskRevisionData: data) {
                listWriter.write(
                    taskRevisionData.getUpdateAt(),
                    taskRevisionData.getName(),
                    taskRevisionData.getDescription(),
                    taskRevisionData.getStatus(),
                    taskRevisionData.getPriority(),
                    taskRevisionData.getSprint(),
                    taskRevisionData.getProject(),
                    taskRevisionData.getAssignee()
                );
            }

            listWriter.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void generatePdf(List<TaskRevisionStatisticDTO> data, boolean lock, HttpServletResponse response) {
        try {
            PdfWriter writer = preparePdfGenerating("Task statistics", response, lock, "UpdateAt", "Name", "Description", "Status", "Priority", "Sprint", "Project", "Assignee");

            for (TaskRevisionStatisticDTO taskRevisionData: data) {
                this.addCell(String.valueOf(taskRevisionData.getUpdateAt()));
                this.addCell(taskRevisionData.getName());
                this.addCell(taskRevisionData.getDescription());
                this.addCell(String.valueOf(taskRevisionData.getStatus()));
                this.addCell(String.valueOf(taskRevisionData.getPriority()));
                this.addCell(taskRevisionData.getSprint());
                this.addCell(taskRevisionData.getProject());
                this.addCell(taskRevisionData.getAssignee());
            }

            finishPdfGenerating();
            writer.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateXls(List<TaskRevisionStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, XLS_FILE_FORMAT);
        setResponseContentType(response, XLS_CONTENT_TYPE);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Task statistics");

        sheet.setDefaultColumnWidth(15);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        CellStyle wrapStyle = workbook.createCellStyle();
        wrapStyle.setWrapText(true);

        sheet.setColumnWidth(0, 256 * 20);
        sheet.setColumnWidth(1, 256 * 25);
        sheet.setColumnWidth(2, 256 * 12);
        sheet.setColumnWidth(3, 256 * 8);
        sheet.setColumnWidth(4, 256 * 10);
        sheet.setColumnWidth(5, 256 * 10);
        sheet.setColumnWidth(6, 256 * 10);


        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Task statistics");


        HSSFRow header = sheet.createRow(1);

        setXlsHeaders(header, style, "UpdateAt", "Name", "Status", "Priority", "Sprint", "Project", "Assignee");

        int rowCount = 2;

        for (TaskRevisionStatisticDTO taskRevisionData: data) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(String.valueOf(taskRevisionData.getUpdateAt()));
            aRow.createCell(1).setCellValue(taskRevisionData.getName());
            aRow.createCell(2).setCellValue(String.valueOf(taskRevisionData.getStatus()));
            aRow.createCell(3).setCellValue(String.valueOf(taskRevisionData.getPriority()));
            aRow.createCell(4).setCellValue(taskRevisionData.getSprint());
            aRow.createCell(5).setCellValue(taskRevisionData.getProject());
            aRow.createCell(6).setCellValue(taskRevisionData.getAssignee());
        } try {
            workbook.write(response.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
