package vn.com.dsk.demo.feature.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.model.User;
import vn.com.dsk.demo.base.repository.UserRepository;
import vn.com.dsk.demo.feature.dto.BookDto;
import vn.com.dsk.demo.feature.dto.PickingOutDetailDto;
import vn.com.dsk.demo.feature.dto.PickingOutDto;
import vn.com.dsk.demo.feature.dto.request.PickingOutRequest;
import vn.com.dsk.demo.feature.model.Book;
import vn.com.dsk.demo.feature.model.PickingOut;
import vn.com.dsk.demo.feature.model.PickingOutDetail;
import vn.com.dsk.demo.feature.repository.BookRepository;
import vn.com.dsk.demo.feature.repository.PickingOutRepository;
import vn.com.dsk.demo.feature.service.PickingOutService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PickingOutServiceImpl implements PickingOutService {

    private final PickingOutRepository pickingOutRepository;

    private final ModelMapper modelMapper;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;


    @Override
    @Transactional
    public String createPickingOut(PickingOutRequest pickingOutRequest) {
        PickingOut pickingOut = modelMapper.map(pickingOutRequest, PickingOut.class);
        List<PickingOutDetail> pickingOutDetails = pickingOutRequest.getPickingOutDetailRequests().stream().map(e -> {
            PickingOutDetail pickingOutDetail = new PickingOutDetail();

            var book = bookRepository.findById(e.getIdBook()).orElseThrow(() -> new EntityNotFoundException(Book.class.getName(), e.getIdBook().toString()));
            pickingOutDetail.setPickingOut(pickingOut);
            pickingOutDetail.setBook(book);
            pickingOutDetail.setQuantity(e.getQuantity());
            return pickingOutDetail;
        }).toList();
        pickingOut.setPickingOutDetails(pickingOutDetails);
        pickingOut.setUser(getCurrentUser());
        pickingOutRepository.save(pickingOut);
        return "PickingOut have been created";
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(User.class.getName(), username));
    }

    public List<PickingOutDto> getAllPickingOut() {
        return pickingOutRepository.findAll().stream().map(e -> {
            var pickingOutDto = modelMapper.map(e, PickingOutDto.class);
            List<PickingOutDetailDto> pickingOutDetailDtos = e.getPickingOutDetails().stream().map(pickingOutDetail -> {
                var pickingOutDetailDto = modelMapper.map(pickingOutDetail, PickingOutDetailDto.class);
                pickingOutDetailDto.setBookDto(modelMapper.map(pickingOutDetail.getBook(), BookDto.class));
                return pickingOutDetailDto;
            }).toList();

            pickingOutDto.setPickingOutDetailDtos(pickingOutDetailDtos);
            return pickingOutDto;
        }).collect(Collectors.toList());
    }

}
