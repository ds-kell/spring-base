package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.Book;
import vn.com.dsk.demo.feature.model.Category;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByCategory(Category category);
}
