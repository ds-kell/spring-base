package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", columnDefinition = "nvarchar(255)")
    private String name;
    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;
}
