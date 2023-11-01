package vn.com.dsk.demo.feature.dto;

import lombok.Data;
import vn.com.dsk.demo.feature.model.Branch;

@Data
public class BookDetailDto {
    private Long id;
    private BookDto bookDto;
    private Branch branch;
    private long quantity;
}
