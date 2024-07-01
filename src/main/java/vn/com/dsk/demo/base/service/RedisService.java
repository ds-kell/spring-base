package vn.com.dsk.demo.base.service;

import vn.com.dsk.demo.base.dto.request.SignupRequest;

public interface RedisService {

    void saveToken(String token, String userData, long duration);

    void saveToken(String token, SignupRequest signupRequest, long duration);

    SignupRequest validateToken(String token);

    void revokeToken(String token);
}
