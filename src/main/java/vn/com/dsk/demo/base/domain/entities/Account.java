package vn.com.dsk.demo.base.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    @NotBlank(message = "*Please provide an username")
    private String username;
    @Column(name = "email", nullable = false)
    @Email(message = "*Please provide a valid Email")
    @NotBlank(message = "*Please provide an email")
    private String email;
//    @JsonIgnore
//    @Column(name = "hashed_password", nullable = false)
//    private String hashedPassword;
//    @JsonIgnore
//    @Column(name = "personal_hashed_code", nullable = false)
//    private String personalHashedCode;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tbl_account_authority",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new HashSet<>();
    @Column(name = "is_active")
    @JsonIgnore
    private int isActive = 0;
    @JsonIgnore
    @Length(min = 3, message = "*Your password must have at least 3 characters")
    @NotBlank(message = "*Please provide your password")
    @Column(name = "password", nullable = false)
    private String password;
}