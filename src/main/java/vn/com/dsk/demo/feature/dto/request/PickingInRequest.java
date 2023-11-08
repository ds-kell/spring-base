package vn.com.dsk.demo.feature.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PickingInRequest {
    @NotNull
    private Integer branchId;
    @NotNull
    private int total;
    @NotNull
    private Date date;
    private String note;
    List<PickingInDetailRequest> pickingInDetailRequests;
}
