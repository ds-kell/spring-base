package vn.com.dsk.demo.feature.multipart_file;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@RequiredArgsConstructor
public class MultipartFileServiceImpl implements MultipartFileService{

    private final FileHelper fileHelper;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
//                byte[] content = file.getBytes();
                InputStream inputStream = file.getInputStream();
                String contentType = file.getContentType();
                assert contentType != null;

                return switch (contentType) {
                    case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> fileHelper.readDocxContent(inputStream);
                    case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> fileHelper.readExcelContent(inputStream);
                    case "application/pdf" -> fileHelper.readPdfContent(inputStream);
                    case "text/csv" -> fileHelper.readCsvContent(inputStream);
                    case "text/plain" -> fileHelper.readPlainTextContent(inputStream);
                    default -> "Unsupported file format " + file.getOriginalFilename();
                };
            } else {
                return "File not found";
            }
        } catch (IOException e) {
            return "Error processing the file";
        }
    }

    @Override
    public void exportFile(MultipartFile file) throws IOException {
        String filePath = "src/main/resources/targetFile." + getFileExtension(file.getContentType());
        File targetFile = new File(filePath);
        file.transferTo(targetFile);

        String existingContent = FileUtils.readFileToString(targetFile, "UTF-8");

        String additionalData = "Addition data";
        String newContent = existingContent + "\n" + additionalData;
        FileUtils.writeStringToFile(targetFile, newContent, "UTF-8");
    }

    private String getFileExtension(String contentType) {
        if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
            return "docx";
        } else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)) {
            return "xlsx";
        } else if ("application/pdf".equals(contentType)) {
            return "pdf";
        } else if ("text/csv".equals(contentType)) {
            return "csv";
        } else if ("text/plain".equals(contentType)) {
            return "txt";
        } else {
            return "unknown";
        }
    }

    @Override
    public void createExcelFile() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet 1");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Column 1");
            headerRow.createCell(1).setCellValue("Column 2");

            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue("Data 1");
            dataRow.createCell(1).setCellValue("Data 2");

            FileOutputStream fileOut = new FileOutputStream("example.xlsx");
            workbook.write(fileOut);
        }
    }

    @Override
    public byte[] toByteArray(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] bytes = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(bytes);
        }
        return bytes;
    }

}