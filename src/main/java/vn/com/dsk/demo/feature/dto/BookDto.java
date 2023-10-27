package vn.com.dsk.demo.feature.dto;

import lombok.Data;
import vn.com.dsk.demo.feature.model.Category;

@Data
public class BookDto {
    private Long id;
    private float importPrice;
    private float exportPrice;
    private int quantity;
    private Category category;
}
