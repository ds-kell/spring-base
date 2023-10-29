package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.PickingIn;

public interface PickingInRepository extends JpaRepository<PickingIn, Long> {
}
