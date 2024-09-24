package vn.com.dsk.demo.base.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.base.domain.entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
