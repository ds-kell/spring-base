package vn.com.dsk.demo.base.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/public/")
public class TestController {

    @GetMapping("user/get-test")
    public String getTest(){
        return "BTT-19-11-2001";
    }

    @PostMapping("user/post-test")
    public String postTest(@RequestParam("query") String query){
        return "BTT-19-11-2001-" + query;
    }
}
