package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import vn.com.dsk.demo.base.model.User;

import java.util.Date;


@Data
@Entity
@Table(name = "tbl_expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "id_branch")
//    private Branch branch;
    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;
    private int total;
    private Date date;
    @Column(name = "note", columnDefinition = "text")
    private String note;
}
