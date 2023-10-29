package vn.com.dsk.demo.feature.dto;

import lombok.Data;
import vn.com.dsk.demo.base.model.User;

import java.util.Date;
import java.util.List;
@Data
public class ProposalDto {
    private Long id;
    private User user;
    private Date date;
    List<ProposalDetailDto> proposalDetailDtos;
}
