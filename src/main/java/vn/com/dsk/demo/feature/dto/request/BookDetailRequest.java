package vn.com.dsk.demo.feature.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDetailRequest {
    @NotNull
    private Integer bookId;
    @NotNull
    private Integer branchId;
    @NotNull
    private int quantity;
}
