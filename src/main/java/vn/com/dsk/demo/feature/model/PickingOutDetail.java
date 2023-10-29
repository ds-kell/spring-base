package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tbl_picking_out_detail")
public class PickingOutDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="id_picking_out")
    private PickingOut pickingOut;
    @ManyToOne
    @JoinColumn(name="id_book")
    private Book book;
    private int quantity;
    private int total;
}
