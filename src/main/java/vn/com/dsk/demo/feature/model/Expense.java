package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import vn.com.dsk.demo.base.model.User;

import java.util.Date;


@Data
@Entity
@Table(name = "tbl_expense")
public class Expense {
    @Id
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name="id_user", nullable = false)
    private User user;
    private int total;
    private Date date;
    @Column(name = "note", columnDefinition = "nvarchar(255)")
    private String note;
}
