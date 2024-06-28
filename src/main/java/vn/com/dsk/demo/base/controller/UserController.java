package vn.com.dsk.demo.base.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.service.UserService;
import vn.com.dsk.demo.base.utils.response.Response;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;

    @GetMapping("private/user/info")
    public ResponseEntity<Response> getUserInfo(){
        return ResponseUtils.ok(userService.getUserInfo());
    }

}
