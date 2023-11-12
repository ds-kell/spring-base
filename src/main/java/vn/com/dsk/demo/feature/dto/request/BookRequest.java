package vn.com.dsk.demo.feature.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank
    private String name;
    private int importPrice;
    private int exportPrice;
    @NotNull
    private Integer categoryId;
}
