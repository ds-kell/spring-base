package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.BookProposal;

public interface BookRequestRepository extends JpaRepository<BookProposal, Long> {
}
