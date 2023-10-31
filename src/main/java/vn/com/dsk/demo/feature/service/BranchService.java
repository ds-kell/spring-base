package vn.com.dsk.demo.feature.service;

import vn.com.dsk.demo.feature.dto.BranchDto;
import vn.com.dsk.demo.feature.dto.request.BranchRequest;

import java.util.List;

public interface BranchService {
    List<BranchDto> getAllBranches();

    String createBranch(BranchRequest branchRequest);
}
