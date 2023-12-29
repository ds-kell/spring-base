package vn.com.dsk.demo.base.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    private String type;

    private String username;

    private List<String> authorities;
}