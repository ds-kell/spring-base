package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.BookDetail;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
}
