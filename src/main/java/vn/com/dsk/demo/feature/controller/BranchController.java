package vn.com.dsk.demo.feature.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.request.BranchRequest;
import vn.com.dsk.demo.feature.service.BranchService;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/branch/")
public class BranchController {

    private final BranchService branchService;

    @GetMapping("all-branch")
    public ResponseEntity<?> getAllBranch(){
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @PostMapping("create-branch")
    public ResponseEntity<?> createBranch(@RequestBody BranchRequest branchRequest){
        return ResponseUtils.ok(branchService.createBranch(branchRequest));
    }
}
