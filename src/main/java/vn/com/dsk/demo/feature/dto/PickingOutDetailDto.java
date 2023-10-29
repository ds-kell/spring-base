package vn.com.dsk.demo.feature.dto;


import lombok.Data;
@Data
public class PickingOutDetailDto {
    private Long id;
    private BookDto bookDto;
    private int quantity;
    private int total;
}
