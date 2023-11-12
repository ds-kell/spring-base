package vn.com.dsk.demo.feature.dto;

import lombok.Data;

@Data
public class BookDto {
    private Integer id;
    private String name;
    private int importPrice;
    private int exportPrice;
    private CategoryDto categoryDto;
}
