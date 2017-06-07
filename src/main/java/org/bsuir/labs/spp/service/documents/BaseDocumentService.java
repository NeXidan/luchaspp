package org.bsuir.labs.spp.service.documents;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.CellStyle;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class BaseDocumentService{
    protected final String CSV_FILE_FORMAT = ".csv";

    protected final String XLS_FILE_FORMAT = ".xls";

    protected final String PDF_FILE_FORMAT = ".pdf";

    protected final String CSV_CONTENT_TYPE = "text/csv; charset=utf-8";

    protected final String PDF_CONTENT_TYPE = "application/pdf";

    protected final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

    protected final String headerKey = "Content-Disposition";

    protected final String pdfTemplatePath = "./template.pdf";

    protected Document pdfDocument;

    protected PdfPTable pdfTable;

    protected String createDocumentName() {
        return java.util.UUID.randomUUID().toString();
    }

    protected String getHeaderValue(String fileName) {
        return String.format("attachment; filename=\"%s\"", fileName);
    }

    protected void setResponseHeader(HttpServletResponse response, String fileFormat) {
        response.setHeader(headerKey, getHeaderValue(createDocumentName() + fileFormat));
    }

    protected void setResponseContentType(HttpServletResponse response, String contentType) {
        response.setContentType(contentType);
    }

    protected void setPdfTableWidth(PdfPTable table, int columnsNumber) {
        float[] widths = new float[columnsNumber];
        for(int i = 0; i < columnsNumber; i++) {
            widths[i] = 2.0f;
        }

        try {
            table.setWidthPercentage(100.0f);
            table.setWidths(widths);
            table.setSpacingBefore(10);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Font getFont() {
        Font font = FontFactory.getFont("/Users/nexidan/git/some3/src/main/resources/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        font.setColor(BaseColor.BLACK);

        return font;
    }

    protected void setPdfHeaders(PdfPTable table, PdfPCell cell, String ... headers) {
        Font font = getFont();

        for (String header: headers) {
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        }
    }

    protected void addCell(String text) {
        Font font = getFont();
        pdfTable.addCell(new Phrase(text, font));
    }

    protected void setXlsHeaders(HSSFRow header, CellStyle style, String ... headers) {
        Integer i = 0;
        for (String head : headers) {
            header.createCell(i).setCellValue(head);
            header.getCell(i).setCellStyle(style);
            i++;
        }
    }

    protected void addPdfMetadata(Document document) {
        document.addAuthor("Spp tracker");
        document.addTitle("Report");
    }

    protected void setPdfLock(PdfWriter writer) throws DocumentException {
        writer.setEncryption(
            null,
            null,
            ~(PdfWriter.ALLOW_COPY),
            PdfWriter.STANDARD_ENCRYPTION_128
        );
    }

    private void addHeader(PdfReader letterhead, PdfWriter writer) {
        PdfContentByte content = writer.getDirectContent();
        PdfImportedPage page = writer.getImportedPage(letterhead, 1);
        content.addTemplate(page, 0, 0);
    }

    protected PdfWriter preparePdfGenerating(String paragraphName, HttpServletResponse response, boolean lock, String ... headers) throws Exception {
        setResponseHeader(response, PDF_FILE_FORMAT);
        setResponseContentType(response, PDF_CONTENT_TYPE);

        PdfReader template = new PdfReader(pdfTemplatePath);
        Rectangle pageSize = template.getPageSizeWithRotation(1);

        int columnsNumber = headers.length;

        Document document = new Document(pageSize);
        this.pdfDocument = document;

        document.setMargins(10, 10, 340, 20);
        document.setMarginMirroringTopBottom(true);

        OutputStream outputStream = response.getOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        addPdfMetadata(document);
        if (lock) {
            setPdfLock(writer);
        }

        document.open();

        addHeader(template, writer);

        document.add(new Paragraph(paragraphName));

        PdfPTable table = new PdfPTable(columnsNumber);
        this.pdfTable = table;

        setPdfTableWidth(table, columnsNumber);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setPadding(5);

        setPdfHeaders(table, cell, headers);

        return writer;
    }

    protected void finishPdfGenerating() throws Exception{
        this.pdfDocument.add(this.pdfTable);

        this.pdfDocument.close();
    }
}
