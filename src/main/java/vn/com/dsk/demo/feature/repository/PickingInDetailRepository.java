package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.PickingInDetail;

public interface PickingInDetailRepository extends JpaRepository<PickingInDetail, String> {
}
