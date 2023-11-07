package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "tbl_proposal_detail")
public class ProposalDetail {
    @Id
    @UuidGenerator
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proposal")
    private Proposal proposal;
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;
    @Column(name = "quantity")
    private Integer quantity;
}