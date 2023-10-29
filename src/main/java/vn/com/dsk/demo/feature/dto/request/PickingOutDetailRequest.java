package vn.com.dsk.demo.feature.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PickingOutDetailRequest {
    @NotNull
    private Long idBook;
    @NotNull
    private int quantity;
    @NotNull
    private int total;
}
