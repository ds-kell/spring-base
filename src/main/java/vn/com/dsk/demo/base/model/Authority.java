package vn.com.dsk.demo.base.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "tbl_role")
public class Authority {
    @Id
    @UuidGenerator
    private String id;
    @NotNull
    private String role;
}
