package vn.com.dsk.demo.feature.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBookRequest {
    @NotNull
    private String id;
    private String name;
    private float importPrice;
    private float exportPrice;
    private int quantity;
    private Integer categoryId;
}
