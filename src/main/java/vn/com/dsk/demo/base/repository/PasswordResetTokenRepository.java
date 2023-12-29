package vn.com.dsk.demo.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.base.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
