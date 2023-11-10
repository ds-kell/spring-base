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
import vn.com.dsk.demo.feature.dto.PickingInDetailDto;
import vn.com.dsk.demo.feature.dto.PickingInDto;
import vn.com.dsk.demo.feature.dto.request.PickingInRequest;
import vn.com.dsk.demo.feature.model.Book;
import vn.com.dsk.demo.feature.model.BookDetail;
import vn.com.dsk.demo.feature.model.PickingIn;
import vn.com.dsk.demo.feature.model.PickingInDetail;
import vn.com.dsk.demo.feature.repository.BookDetailRepository;
import vn.com.dsk.demo.feature.repository.BookRepository;
import vn.com.dsk.demo.feature.repository.PickingInRepository;
import vn.com.dsk.demo.feature.service.PickingInService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PickingInServiceImpl implements PickingInService {

    private final PickingInRepository pickingInRepository;

    private final ModelMapper modelMapper;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final BookDetailRepository bookDetailRepository;

    @Override
    public String createPickingIn(PickingInRequest pickingInRequest) {
        PickingIn pickingIn = modelMapper.map(pickingInRequest, PickingIn.class);
        var user = getCurrentUser();
        List<PickingInDetail> pickingInDetails = pickingInRequest.getPickingInDetailRequests().stream()
                .map(pickingInDetailRequest -> {

                    PickingInDetail pickingInDetail = new PickingInDetail();

                    var book = bookRepository.findById(pickingInDetailRequest.getBookId()).orElseThrow(() -> new EntityNotFoundException(Book.class.getName(), pickingInDetailRequest.getBookId().toString()));

                    var bookDetail = bookDetailRepository.findBookDetailByBookIdAndBranchId(pickingInDetailRequest.getBookId(), user.getBranch().getId());

                    if (bookDetail == null) {
                        bookDetail = new BookDetail();
                        bookDetail.setQuantity(0);
                        bookDetail.setBranch(user.getBranch());
                        bookDetail.setBook(book);
                        try {
                            bookDetailRepository.save(bookDetail);
                        } catch (Exception exception) {
                            log.error("Error create book detail", exception);
                        }
                    }
                    pickingInDetail.setPickingIn(pickingIn);
                    pickingInDetail.setBook(book);
                    pickingInDetail.setQuantity(pickingInDetailRequest.getQuantity());
                    return pickingInDetail;
                }).toList();
        pickingIn.setPickingInDetails(pickingInDetails);
        pickingIn.setUser(getCurrentUser());
        pickingInRepository.save(pickingIn);
        return "PickingIn have been created";
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(User.class.getName(), username));
    }

    @Override
    @Transactional
    public List<PickingInDto> getAllPickingIn() {
        return pickingInRepository.findAll().stream().map(e -> {
            var pickingInDto = modelMapper.map(e, PickingInDto.class);
            List<PickingInDetailDto> pickingInDetailDtos = e.getPickingInDetails().stream().map(pickingInDetail -> {
                var pickingInDetailDto = modelMapper.map(pickingInDetail, PickingInDetailDto.class);
                pickingInDetailDto.setBookDto(modelMapper.map(pickingInDetail.getBook(), BookDto.class));
                return pickingInDetailDto;
            }).toList();
            pickingInDto.setPickingInDetailDtos(pickingInDetailDtos);
            return pickingInDto;
        }).collect(Collectors.toList());
    }
}
