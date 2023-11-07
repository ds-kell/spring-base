package vn.com.dsk.demo.feature.dto;

import lombok.Data;
import vn.com.dsk.demo.feature.model.BookDetail;

import java.util.UUID;

@Data
public class ProposalDetailDto {
    private String id;
    private BookDetail book;
    private Integer quantity;
}
