package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "tbl_book")
public class Book {
    @Id
    @UuidGenerator
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "import_price")
    private float importPrice;
    @Column(name = "export_price")
    private float exportPrice;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "is_active")
    private Boolean isActive = true;
}
