package vn.com.dsk.demo.feature.service;

import vn.com.dsk.demo.feature.dto.PickingInDto;
import vn.com.dsk.demo.feature.dto.request.PickingInRequest;

import java.util.List;

public interface PickingInService {

    String createPickingIn(PickingInRequest pickingInRequest);

    List<PickingInDto> getAllPickingIn();
}
