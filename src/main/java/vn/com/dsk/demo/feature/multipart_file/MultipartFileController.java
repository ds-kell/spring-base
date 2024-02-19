package vn.com.dsk.demo.feature.multipart_file;

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

    @GetMapping("/download")
    public ResponseEntity<?> downloadExcel() throws IOException {
        multipartFileService.createExcelFile();
        ByteArrayResource resource = new ByteArrayResource(multipartFileService.toByteArray("example.xlsx"));
//        InputStreamResource inputStream = new InputStreamResource(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"example.xlsx\"")
                .body(resource);
    }
}
/*https://github.com/EnggAdda/SpringBootDownloadGetAPIDtaInExcelFile/blob/master/src/main/java/com/example/springbootdownloadexcelfile/controller/ProductController.java*/