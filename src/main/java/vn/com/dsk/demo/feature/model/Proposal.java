package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import vn.com.dsk.demo.base.model.User;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_proposal")
public class Proposal {
    @Id
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    private Date date;
    @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ProposalDetail> proposalDetails;
}
