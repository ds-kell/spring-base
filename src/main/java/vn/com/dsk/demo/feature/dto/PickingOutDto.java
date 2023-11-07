package vn.com.dsk.demo.feature.dto;

import lombok.Data;
import vn.com.dsk.demo.base.model.User;

import java.util.Date;
import java.util.List;

@Data
public class PickingOutDto {
    private String id;
    private User user;
    private int total;
    private Date date;
    private String note;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    List<PickingOutDetailDto> pickingOutDetailDtos;
}
