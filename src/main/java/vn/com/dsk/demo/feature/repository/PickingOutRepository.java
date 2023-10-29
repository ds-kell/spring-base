package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.PickingOut;

public interface PickingOutRepository extends JpaRepository<PickingOut, Long> {
}
