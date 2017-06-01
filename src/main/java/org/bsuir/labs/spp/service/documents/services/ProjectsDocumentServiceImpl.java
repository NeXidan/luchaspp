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
import org.bsuir.labs.spp.service.documents.dto.ProjectStatisticDTO;
import org.bsuir.labs.spp.service.documents.interfaces.ProjectsDocumentService;
import org.bsuir.labs.spp.service.documents.interfaces.SprintDocumentService;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ProjectsDocumentServiceImpl extends BaseDocumentService implements ProjectsDocumentService {
    @Override
    public void generateCsv(List<ProjectStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, CSV_FILE_FORMAT);
        setResponseContentType(response, CSV_CONTENT_TYPE);

        try {
            ICsvListWriter listWriter = new CsvListWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

            listWriter.writeHeader("Id", "Name", "Description", "Status", "Managers");

            for (ProjectStatisticDTO projectData: data) {
                listWriter.write(projectData.getId(), projectData.getName(), projectData.getDescription(),
                    projectData.getStatus(), projectData.getManagers());
            }

            listWriter.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void generatePdf(List<ProjectStatisticDTO> data, boolean lock, HttpServletResponse response) {
        try {
            PdfWriter writer = preparePdfGenerating("Projects statistics", response, lock, "Id", "Name", "Description", "Status", "Managers");

            for (ProjectStatisticDTO projectData: data) {
                this.pdfTable.addCell(String.valueOf(projectData.getId()));
                this.pdfTable.addCell(projectData.getName());
                this.pdfTable.addCell(projectData.getDescription());
                this.pdfTable.addCell(String.valueOf(projectData.getStatus()));
                this.pdfTable.addCell(String.valueOf(projectData.getManagers()));
            }

            finishPdfGenerating();
            writer.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateXls(List<ProjectStatisticDTO> data, HttpServletResponse response) {
        setResponseHeader(response, XLS_FILE_FORMAT);
        setResponseContentType(response, XLS_CONTENT_TYPE);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Projects statistics");

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

        setXlsHeaders(header, style, "Id", "Name", "Description", "Status", "Managers");

        int rowCount = 1;

        for (ProjectStatisticDTO projectData: data) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(projectData.getId());
            aRow.createCell(1).setCellValue(projectData.getName());
            aRow.createCell(2).setCellValue(projectData.getDescription());
            aRow.createCell(3).setCellValue(String.valueOf(projectData.getStatus()));
            aRow.createCell(4).setCellValue(String.valueOf(projectData.getManagers()));
        }

        try {
            workbook.write(response.getOutputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
