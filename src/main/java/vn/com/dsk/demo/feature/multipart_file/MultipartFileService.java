package vn.com.dsk.demo.feature.multipart_file;

import com.itextpdf.text.DocumentException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MultipartFileService {
    String uploadFile(MultipartFile file) throws IOException;

    void exportFile(MultipartFile file) throws IOException;

    InputStreamResource createExcelFile() throws IOException;

    InputStreamResource createPdfFile(String content) throws IOException, DocumentException;

}