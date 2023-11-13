package vn.com.dsk.demo.feature.dto;

import lombok.Data;

import java.util.List;

@Data
public class AllBookDto {
    private BookDto bookDto;
    private List<DetailByBookDto> bookDetailDtos;
}
