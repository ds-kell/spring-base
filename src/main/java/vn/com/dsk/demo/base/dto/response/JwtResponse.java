package vn.com.dsk.demo.base.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    private String type;

    private String username;

    private String role;

    public JwtResponse() {

    }
}