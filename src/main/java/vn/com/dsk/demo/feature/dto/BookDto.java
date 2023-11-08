package vn.com.dsk.demo.feature.dto;

import lombok.Data;

@Data
public class BookDto {
    private Integer id;
    private String name;
    private Integer importPrice;
    private Integer exportPrice;
    private int quantity;
    private CategoryDto categoryDto;
}
