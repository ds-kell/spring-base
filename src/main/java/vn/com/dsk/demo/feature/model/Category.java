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
    private String name;
    @Column(name = "description", columnDefinition = "text")
    private String description;
}
