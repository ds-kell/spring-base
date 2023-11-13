package vn.com.dsk.demo.feature.dto;

import lombok.Data;
@Data
public class DetailByBookDto {
    private String id;
    private BranchDto branchDto;
    private long quantity;
}
