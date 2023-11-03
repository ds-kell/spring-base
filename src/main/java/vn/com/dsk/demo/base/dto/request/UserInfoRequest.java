package vn.com.dsk.demo.base.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoRequest {
    private String email;
    private String fullName;
    private String address;
    private Date dob;
}
