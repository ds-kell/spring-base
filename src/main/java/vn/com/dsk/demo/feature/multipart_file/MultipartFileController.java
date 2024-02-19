package vn.com.dsk.demo.feature.multipart_file;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/public/multipart-file/")
public class MultipartFileController {

    private final MultipartFileService multipartFileService;

    @PostMapping(path = "upload-ver1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFileVer1(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseUtils.ok(multipartFileService.uploadFile(file));
    }

    @PostMapping(path = "upload-ver2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFileVer2(@RequestPart("file") MultipartFile file) throws IOException {
        return ResponseUtils.ok(multipartFileService.uploadFile(file));
    }

    @GetMapping("/downloadExcel")
    public ResponseEntity<?> downloadExcel() throws IOException {
        String fileName ="example.xlsx";
        InputStreamResource response = multipartFileService.createExcelFile();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(response);
    }
    @PostMapping("/downloadPdf")
    public ResponseEntity<?> downloadPdf(String content) throws IOException, DocumentException {
        String fileName ="example.pdf";
        InputStreamResource response = multipartFileService.createPdfFile(content);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(response);
    }
}
