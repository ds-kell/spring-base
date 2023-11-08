package vn.com.dsk.demo.base.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.dto.request.UserInfoRequest;
import vn.com.dsk.demo.base.dto.request.UserRequest;
import vn.com.dsk.demo.base.service.UserService;
import vn.com.dsk.demo.base.utils.response.Response;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;

    @GetMapping("private/all-user")
    public ResponseEntity<?> getAllUser() {
        return ResponseUtils.ok(userService.getAllUser());
    }

    @GetMapping("private/user/info")
    public ResponseEntity<?> getUserInfo() {
        return ResponseUtils.ok(userService.getUserInfo());
    }

    @PutMapping("private/user/update-info")
    public ResponseEntity<?> updateInfo(@RequestBody UserInfoRequest userInfoRequest) {
        return ResponseUtils.ok(userService.updateInfo(userInfoRequest));
    }

    @PostMapping("public/user/create-user")
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseUtils.ok(userService.createUser(userRequest));
    }

    @PutMapping("private/user/update-workplace")
    public ResponseEntity<?> updateInfo(@RequestBody Integer branchId) {
        return ResponseUtils.ok(userService.updateWorkplace(branchId));
    }

    @GetMapping("admin/user/test")
    public String getTest() {
        return "BTT-19-11-2001";
    }

    @GetMapping("private/user/{userId}")
    public ResponseEntity<?> getUserInfoById(@PathVariable String userId) {
        return ResponseUtils.ok(userService.getUserInfoById(userId));
    }
    @DeleteMapping("private/user/deactivate/{userId}")
    public ResponseEntity<?> deactivateUserById(@PathVariable String userId) {
        return ResponseUtils.ok(userService.deactivateUser(userId));
    }

}
