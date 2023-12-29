package vn.com.dsk.demo.base.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank
    @Size(min = 5, max = 40)
    private String newPassword;
}
