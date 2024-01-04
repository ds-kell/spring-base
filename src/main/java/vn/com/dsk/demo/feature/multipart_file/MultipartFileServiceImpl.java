package vn.com.dsk.demo.feature.multipart_file;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
}