package vn.com.dsk.demo.base.service;


import vn.com.dsk.demo.base.dto.UserDto;
import vn.com.dsk.demo.base.dto.request.UserRequest;
import vn.com.dsk.demo.base.dto.response.JwtResponse;

public interface UserService {

    UserDto getUserInfo();

    JwtResponse createUser(UserRequest userRequest);

}
