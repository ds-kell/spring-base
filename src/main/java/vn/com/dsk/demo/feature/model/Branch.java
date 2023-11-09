package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", columnDefinition = "nvarchar(255)")
    private String name;
    @Column(name = "address", columnDefinition = "nvarchar(255)")
    private String address;
}
