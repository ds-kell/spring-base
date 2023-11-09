package vn.com.dsk.demo.statistic;

import lombok.Data;

import java.util.Date;

@Data
public class TimeRequest {

    private Integer branchId;

    private Date startDate;

    private Date endDate;
}
