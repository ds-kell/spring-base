package vn.com.dsk.demo.feature.multipart_file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MultipartFileService {
    String uploadFile(MultipartFile file) throws IOException;

    void exportFile(MultipartFile file) throws IOException;
}