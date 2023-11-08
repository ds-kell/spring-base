package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import vn.com.dsk.demo.base.model.User;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_picking_out")
public class PickingOut {
    @Id
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_branch")
    private Branch branch;
    private int total;
    private Date date;
    @Column(name = "note", columnDefinition = "text")
    private String note;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "customer_email")
    private String customerEmail;
    @OneToMany(mappedBy = "pickingOut", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<PickingOutDetail> pickingOutDetails;
}