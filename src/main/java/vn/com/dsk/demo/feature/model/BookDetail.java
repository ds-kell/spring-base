package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name="tbl_book_detail")
public class BookDetail {
    @Id
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_book")
    private Book book;
    @ManyToOne
    @JoinColumn(name="id_branch")
    private Branch branch;
    private int quantity;
}
