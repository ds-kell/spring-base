package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.ProposalDetail;

public interface BookRequestRepository extends JpaRepository<ProposalDetail, String> {
}
