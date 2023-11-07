package vn.com.dsk.demo.feature.dto;


import lombok.Data;
import vn.com.dsk.demo.base.model.User;

import java.util.Date;
@Data
public class ExpenseDto {
    private String id;
    private User user;
    private int total;
    private Date date;
    private String note;
}
