package vn.com.dsk.demo.feature.dto;


import lombok.Data;
@Data
public class PickingInDetailDto {
    private Long id;
    private BookDto bookDto;
    private int quantity;
    private int total;
}
