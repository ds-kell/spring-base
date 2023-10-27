package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
