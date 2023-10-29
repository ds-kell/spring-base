package vn.com.dsk.demo.feature.dto.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProposalRequest {
    private Date date;
    private List<BookProposalRequest> listBookRq;
}
