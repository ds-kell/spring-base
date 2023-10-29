package vn.com.dsk.demo.feature.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ExpenseRequest {

    @NotBlank
    private String name;
    @NotBlank
    private int total;
    @NotNull
    private Date date;
    private String note;
}
