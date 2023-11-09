package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name="tbl_picking_out_detail")
public class PickingOutDetail {
    @Id
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name="id_picking_out", nullable = false)
    private PickingOut pickingOut;
    @ManyToOne
    @JoinColumn(name="id_book", nullable = false)
    private Book book;
    private int quantity;
    private int total;
}
