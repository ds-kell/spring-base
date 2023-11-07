package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "tbl_category")
public class Category {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    @Column(name = "description", columnDefinition = "text")
    private String description;
}
