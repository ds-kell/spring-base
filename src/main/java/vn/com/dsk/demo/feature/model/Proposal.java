package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import vn.com.dsk.demo.base.model.Account;

import java.util.List;

@Data
@Entity
@Table(name = "tbl_proposal")
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL)
    List<BookProposal> bookProposals;
}
