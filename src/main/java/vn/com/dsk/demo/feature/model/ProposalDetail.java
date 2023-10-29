package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_book_proposal")
public class ProposalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proposal")
    private Proposal proposal;
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;
    @Column(name = "quantity")
    private Integer quantity;
}