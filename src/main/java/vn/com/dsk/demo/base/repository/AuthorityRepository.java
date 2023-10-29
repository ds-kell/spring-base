package vn.com.dsk.demo.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.base.model.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByRole(String name);
}
