package vn.com.dsk.demo.base.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.application.services.RedisService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/")
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/generate")
    public String generateToken(@RequestParam String userData) {
        String token = "token";
        redisService.saveToken(token, userData, 3600);
        return token;
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
//        String userData = redisService.validateToken(token);
        return "1235";
    }

    @DeleteMapping("/revoke")
    public String revokeToken(@RequestParam String token) {
        redisService.revokeToken(token);
        return "Token đã bị thu hồi";
    }
}
