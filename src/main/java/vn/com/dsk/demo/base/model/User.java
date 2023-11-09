package vn.com.dsk.demo.base.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import vn.com.dsk.demo.feature.model.Branch;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Table(name= "tbl_user")
public class User {
    @Id
    @UuidGenerator
    private String id;
    @Column(name="username", nullable = false)
    private String username;
    @Column(name="email", nullable = false)
    private String email;
    @JsonIgnore
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tbl_user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Authority> authorities = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "id_branch", nullable = false)
    private Branch branch;
    @Column(name = "full_name", columnDefinition = "nvarchar(255)")
    private String fullName;
    @Column(name = "address", columnDefinition = "nvarchar(255)")
    private String address;
    @Column(name= "dob")
    private Date dob;
    @Column(name = "is_active")
    private Boolean isActive = true;
}
