package vn.com.dsk.demo.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.model.User;
import vn.com.dsk.demo.base.repository.UserRepository;
import vn.com.dsk.demo.feature.repository.*;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService{

    private final UserRepository userRepository;

    private final BranchRepository branchRepository;

    private final PickingInRepository pickingInRepository;

    private final PickingOutRepository pickingOutRepository;

    private final PickingOutDetailRepository pickingOutDetailRepository;

    private final PickingInDetailRepository pickingInDetailRepository;

    @Override
    public StatisticResponse getStatistic(TimeRequest timeRequest) {
        var user = getCurrentUser();
        if (timeRequest.getBranchId() == null)
            timeRequest.setBranchId(user.getBranch().getId());

//        List<PickingIn> pickingInList = pickingInRepository.find
        return null;
    }


    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), username));
    }
}
