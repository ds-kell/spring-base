package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name="tbl_picking_in_detail")
public class PickingInDetail {
    @Id
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name="id_picking_in", nullable = false)
    private PickingIn pickingIn;
    @ManyToOne
    @JoinColumn(name="id_book", nullable = false)
    private Book book;
    private int quantity;
    private int total;
}
