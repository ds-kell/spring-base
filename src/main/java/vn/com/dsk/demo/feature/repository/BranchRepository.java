package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {
}
