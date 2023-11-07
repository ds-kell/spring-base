package vn.com.dsk.demo.feature.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PickingInDetailRequest {
    @NotNull
    private String idBook;
    @NotNull
    private int quantity;
    @NotNull
    private int total;
}
