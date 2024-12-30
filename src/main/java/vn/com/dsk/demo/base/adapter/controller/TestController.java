package vn.com.dsk.demo.base.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.adapter.wrappers.Response;
import vn.com.dsk.demo.base.adapter.wrappers.ResponseUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/public/")
public class TestController {

    @PostMapping("test/post-test")
    public String postTest(@RequestParam("query") String query) {
        return "BTT-19-11-2001-" + query;
    }

    @GetMapping("test/get-test")
    public ResponseEntity<Response> getTest() {
        return ResponseUtils.created("BTT-19-11-2001");

    }    @GetMapping("test/get-test2")
    public ResponseEntity<Response> getTest2() {
        return ResponseUtils.created("BTT-19-11-2001");
    }
}