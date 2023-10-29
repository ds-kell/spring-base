package vn.com.dsk.demo.feature.service;

import vn.com.dsk.demo.feature.dto.PickingOutDto;
import vn.com.dsk.demo.feature.dto.request.PickingOutRequest;

import java.util.List;

public interface PickingOutService {

    String createPickingOut(PickingOutRequest pickingOutRequest);

    List<PickingOutDto> getAllPickingOut();
}
