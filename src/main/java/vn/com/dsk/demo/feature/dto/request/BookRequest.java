package vn.com.dsk.demo.feature.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank
    private String name;
    private float importPrice;
    private float exportPrice;
    private Long categoryId;
}
