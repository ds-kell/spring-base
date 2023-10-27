package vn.com.dsk.demo.base.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.dsk.demo.base.service.UserService;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;

    @GetMapping("private/user/info")
    public ResponseEntity<?> getUserInfo(){
        return ResponseUtils.ok(userService.getUserInfo());
    }

    @GetMapping("public/user/test")
    public String getTest(){
        return "BTT-19-11-2001";
    }
}
