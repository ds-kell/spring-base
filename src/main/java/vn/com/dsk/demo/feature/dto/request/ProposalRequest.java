package vn.com.dsk.demo.feature.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProposalRequest {
    private List<BookProposalRequest> listBookRq;
}
