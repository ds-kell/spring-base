package vn.com.dsk.demo.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.request.ProposalRequest;
import vn.com.dsk.demo.feature.service.ProposalService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/private/proposal/")
public class ProposalController {

    private final ProposalService proposalService;
    @PostMapping("create-proposal")
    public ResponseEntity<?> addProposal(@RequestBody ProposalRequest proposalRequest){
        proposalService.createProposal(proposalRequest);
        return ResponseUtils.ok("Created");
    }
}
