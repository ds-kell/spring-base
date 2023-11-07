package vn.com.dsk.demo.feature.dto;

import lombok.Data;
import vn.com.dsk.demo.feature.model.Branch;

@Data
public class BookDetailDto {
    private String id;
    private BookDto bookDto;
    private Branch branch;
    private long quantity;
}
