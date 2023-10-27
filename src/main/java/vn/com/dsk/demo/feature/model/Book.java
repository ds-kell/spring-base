package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name = "import_price")
    private float importPrice;
    @Column(name = "export_price")
    private float exportPrice;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
