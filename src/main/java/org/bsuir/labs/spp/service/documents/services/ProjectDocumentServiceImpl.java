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
import org.bsuir.labs.spp.service.documents.dto.TaskStatisticDTO;
import org.bsuir.labs.spp.service.documents.interfaces.ProjectDocumentService;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ProjectDocumentServiceImpl extends BaseDocumentService implements ProjectDocumentService {
    @Override
    public void generateCsv(List<TaskStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, CSV_FILE_FORMAT);
        setResponseContentType(response, CSV_CONTENT_TYPE);

        try {
            ICsvListWriter listWriter = new CsvListWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

            listWriter.writeHeader("Id", "Name", "Description", "Status", "Priority", "Sprint", "Project", "Assignee");

            for (TaskStatisticDTO taskData: data) {
                listWriter.write(
                    taskData.getId(),
                    taskData.getName(),
                    taskData.getDescription(),
                    taskData.getStatus(),
                    taskData.getPriority(),
                    taskData.getSprint(),
                    taskData.getProject(),
                    taskData.getAssignee()
                );
            }

            listWriter.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void generatePdf(List<TaskStatisticDTO> data, boolean lock, HttpServletResponse response) {
        try {
            PdfWriter writer = preparePdfGenerating("Project statistics", response, lock, "Id", "Name", "Description", "Status", "Priority", "Sprint", "Project", "Assignee");

            for (TaskStatisticDTO taskData: data) {
                this.pdfTable.addCell(String.valueOf(taskData.getId()));
                this.pdfTable.addCell(taskData.getName());
                this.pdfTable.addCell(taskData.getDescription());
                this.pdfTable.addCell(String.valueOf(taskData.getStatus()));
                this.pdfTable.addCell(String.valueOf(taskData.getPriority()));
                this.pdfTable.addCell(taskData.getSprint());
                this.pdfTable.addCell(taskData.getProject());
                this.pdfTable.addCell(taskData.getAssignee());
            }

            finishPdfGenerating();
            writer.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateXls(List<TaskStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, XLS_FILE_FORMAT);
        setResponseContentType(response, XLS_CONTENT_TYPE);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Project statistics");

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

        setXlsHeaders(header, style, "Id", "Name", "Description", "Status", "Priority", "Sprint", "Project", "Assignee");

        int rowCount = 1;

        for (TaskStatisticDTO taskData: data) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(String.valueOf(taskData.getId()));
            aRow.createCell(1).setCellValue(taskData.getName());
            aRow.createCell(2).setCellValue(taskData.getDescription());
            aRow.createCell(3).setCellValue(String.valueOf(taskData.getStatus()));
            aRow.createCell(4).setCellValue(String.valueOf(taskData.getPriority()));
            aRow.createCell(5).setCellValue(taskData.getSprint());
            aRow.createCell(6).setCellValue(taskData.getProject());
            aRow.createCell(7).setCellValue(taskData.getAssignee());
        } try {
            workbook.write(response.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
