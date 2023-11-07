package vn.com.dsk.demo.feature.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.model.User;
import vn.com.dsk.demo.base.repository.UserRepository;
import vn.com.dsk.demo.feature.dto.ProposalDetailDto;
import vn.com.dsk.demo.feature.dto.ProposalDto;
import vn.com.dsk.demo.feature.dto.request.ProposalRequest;
import vn.com.dsk.demo.feature.model.Book;
import vn.com.dsk.demo.feature.model.Proposal;
import vn.com.dsk.demo.feature.model.ProposalDetail;
import vn.com.dsk.demo.feature.repository.BookRepository;
import vn.com.dsk.demo.feature.repository.ProposalRepository;
import vn.com.dsk.demo.feature.service.ProposalService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    @Override
    public String createProposal(ProposalRequest proposalRequest) {
        Proposal proposal = new Proposal();
        var user = getCurrentUser();
        proposal.setUser(user);
        List<ProposalDetail> listProposalDetail = proposalRequest.getListBookRq().stream().map(e -> {
            ProposalDetail proposalDetail = new ProposalDetail();

            var book = bookRepository.findById(e.getIdBook()).orElseThrow(() -> new EntityNotFoundException(Book.class.getName(), e.getIdBook()));

            proposalDetail.setProposal(proposal);
            proposalDetail.setBook(book);
            proposalDetail.setQuantity(e.getQuantity());
            return proposalDetail;
        }).toList();
        proposal.setProposalDetails(listProposalDetail);
        proposalRepository.save(proposal);
        return "Proposal have been created";
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(User.class.getName(), username));
    }

    @Override
    @Transactional
    public List<ProposalDto> getAllProposal() {
        return proposalRepository.findAll().stream().map(e -> {
            var proposalDto = modelMapper.map(e, ProposalDto.class);
            List<ProposalDetailDto> proposalDetailDtos = e.getProposalDetails().stream().map(proposalDetail -> modelMapper.map(proposalDetail, ProposalDetailDto.class)).collect(Collectors.toList());
            proposalDto.setProposalDetailDtos(proposalDetailDtos);
            return proposalDto;
        }).collect(Collectors.toList());
    }
}
