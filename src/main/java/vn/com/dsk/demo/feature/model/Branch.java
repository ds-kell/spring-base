package vn.com.dsk.demo.feature.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "tbl_branch")
public class Branch {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String address;
}
