package vn.com.dsk.demo.feature.dto;

import lombok.Data;
import vn.com.dsk.demo.feature.model.BookDetail;

@Data
public class ProposalDetailDto {
    private Long id;
    private BookDetail book;
    private Integer quantity;
}
