package vn.com.dsk.demo.feature.multipart_file;


import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.stream.Collectors;

@Component
public class FileHelper {

    public String readDocxContent(byte[] content) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(content);
             XWPFDocument document = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

            return extractor.getText();
        }
    }
    public String readDocxContent(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream)) {
            StringBuilder contentBuilder = new StringBuilder();
            document.getParagraphs().forEach(paragraph -> {
                paragraph.getRuns().forEach(run -> {
                    String text = run.getText(0);
                    if (text != null) {
                        contentBuilder.append(text);
                    }
                });
            });
            document.getAllPictures().forEach(picture -> {
                byte[] pictureData = picture.getData();
            });

            return contentBuilder.toString();
        }
    }

    public String readExcelContent(InputStream inputStream) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            StringBuilder contentBuilder = new StringBuilder();
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);

                for (Row row : sheet) {
                    for (Cell cell : row) {
                        String cellValue = getCellValueAsString(cell);
                        contentBuilder.append(cellValue).append("\t");
                    }
                    contentBuilder.append("\n");
                }
                contentBuilder.append("\n");
            }

            return contentBuilder.toString();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    public String readPdfContent(InputStream inputStream) throws IOException {
        PdfReader reader = new PdfReader(inputStream);
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            String pageContent = PdfTextExtractor.getTextFromPage(reader, i);
            contentBuilder.append(pageContent);
        }

        return contentBuilder.toString();
    }

    public String readCsvContent(InputStream inputStream) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }

        return contentBuilder.toString();
    }

    public String readPlainTextContent(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
