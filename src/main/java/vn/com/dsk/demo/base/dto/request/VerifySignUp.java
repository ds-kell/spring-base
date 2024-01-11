package vn.com.dsk.demo.base.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class VerifySignUp {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> authorities;
    @NotBlank
    @Size(min = 5, max = 40)
    private String password;
    @NotBlank
    @Size(min = 6, max = 6)
    private String verifyCode;
}
