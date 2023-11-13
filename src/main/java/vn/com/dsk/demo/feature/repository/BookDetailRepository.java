package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.BookDetail;

import java.util.List;
public interface BookDetailRepository extends JpaRepository<BookDetail, String> {
    BookDetail findBookDetailById(String id);

    BookDetail findBookDetailByBookIdAndBranchId(Integer bookId, Integer branchId);

    List<BookDetail> findAllBookDetailByBookId(Integer bookId);
}
