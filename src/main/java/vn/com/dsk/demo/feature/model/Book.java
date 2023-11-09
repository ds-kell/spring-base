package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;
    @Column(name = "import_price")
    private int importPrice;
    @Column(name = "export_price")
    private int exportPrice;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "is_active")
    private Boolean isActive = true;
}
