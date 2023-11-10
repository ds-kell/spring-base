package vn.com.dsk.demo.base.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
@Data
public class UserRequest {
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String role;
    @NotBlank
    @Size(min = 5, max = 40)
    private String password;
    @NotNull
    private Integer branchId;
    private String fullName;
    private String address;
    private Date dob;
}
