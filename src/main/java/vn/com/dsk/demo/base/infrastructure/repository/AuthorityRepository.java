package vn.com.dsk.demo.base.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.base.domain.entities.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(String name);
}
