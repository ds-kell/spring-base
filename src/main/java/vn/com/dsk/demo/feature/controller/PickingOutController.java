package vn.com.dsk.demo.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.request.PickingOutRequest;
import vn.com.dsk.demo.feature.service.PickingOutService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/private/picking-out/")
public class PickingOutController {


    private final PickingOutService pickingOutService;

    @GetMapping("all-picking-out")
    public ResponseEntity<?> getAllPickingOut() {
        return ResponseUtils.ok(pickingOutService.getAllPickingOut());
    }

    @PostMapping("create-picking-out")
    public ResponseEntity<?> createPickingOut(@RequestBody PickingOutRequest pickingOutRequest) {
        return ResponseUtils.ok(pickingOutService.createPickingOut(pickingOutRequest));
    }
}
