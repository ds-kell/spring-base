package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.PickingOutDetail;

public interface PickingOutDetailRepository extends JpaRepository<PickingOutDetail, Long> {
}
