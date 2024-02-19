package vn.com.dsk.demo.feature.multipart_file;

import lombok.Data;

@Data
public class PdfModel {
    private String fileName;
    private String content;
    private String author;
}
