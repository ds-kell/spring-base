package vn.com.dsk.demo.feature.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PickingOutRequest {
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    @NotNull
    private int total;
    @NotNull
    private Date date;
    private String note;
    List<PickingOutDetailRequest> PickingOutDetailRequests;
}
