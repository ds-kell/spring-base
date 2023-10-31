package vn.com.dsk.demo.feature.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.feature.dto.BranchDto;
import vn.com.dsk.demo.feature.dto.request.BranchRequest;
import vn.com.dsk.demo.feature.model.Branch;
import vn.com.dsk.demo.feature.repository.BranchRepository;
import vn.com.dsk.demo.feature.service.BranchService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<BranchDto> getAllBranches() {
        return branchRepository.findAll().stream().map(
                e -> modelMapper.map(e, BranchDto.class)
        ).toList();
    }

    @Override
    public String createBranch(BranchRequest branchRequest) {
        branchRepository.save(modelMapper.map(branchRequest, Branch.class));
        return "Branch have been created";
    }
}
