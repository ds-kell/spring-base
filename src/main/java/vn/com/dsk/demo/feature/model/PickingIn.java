package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import vn.com.dsk.demo.base.model.User;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tbl_picking_in")
public class PickingIn {
    @Id
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    private int total;
    private Date date;
    @Column(name = "note", columnDefinition = "text")
    private String note;
    @OneToMany(mappedBy = "pickingIn", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<PickingInDetail> pickingInDetails;
}
