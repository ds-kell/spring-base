package vn.com.dsk.demo.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.request.PickingInDetailRequest;
import vn.com.dsk.demo.feature.dto.request.PickingInRequest;
import vn.com.dsk.demo.feature.service.PickingInService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/private/picking-in/")
public class PickingInController {

    private final PickingInService pickingInService;

    @GetMapping("all-picking-in")
    public ResponseEntity<?> getAllPickingIn(){
        return ResponseUtils.ok(pickingInService.getAllPickingIn());
    }

    @PostMapping("create-picking-in")
    public ResponseEntity<?> createPickingIn(@RequestBody PickingInRequest pickingInRequest){
        return ResponseUtils.ok(pickingInService.createPickingIn(pickingInRequest));
    }
}
