package vn.com.dsk.demo.base.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
