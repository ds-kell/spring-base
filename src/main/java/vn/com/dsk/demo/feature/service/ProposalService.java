package vn.com.dsk.demo.feature.service;

import vn.com.dsk.demo.feature.dto.ProposalDto;
import vn.com.dsk.demo.feature.dto.request.ProposalRequest;

import java.util.List;


public interface ProposalService {
    String createProposal(ProposalRequest proposalRequest);

    List<ProposalDto> getAllProposal();
}
