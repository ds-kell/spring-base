package vn.com.dsk.demo.feature.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.Book;
import vn.com.dsk.demo.feature.model.Category;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByCategory(Category category);

    List<Book> findAllByIsActive(Boolean isActive);

    List<Book> findAllByIsActive(Boolean isActive, Pageable pageable);

    List<Book> findAllByIsActive(Boolean isActive, PageRequest pageRequest);

}
