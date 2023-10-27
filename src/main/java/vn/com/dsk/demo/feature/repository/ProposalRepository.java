package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
}
