package vn.com.dsk.demo.feature.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.model.Account;
import vn.com.dsk.demo.base.repository.AccountRepository;
import vn.com.dsk.demo.feature.dto.request.ProposalRequest;
import vn.com.dsk.demo.feature.model.Book;
import vn.com.dsk.demo.feature.model.BookProposal;
import vn.com.dsk.demo.feature.model.Proposal;
import vn.com.dsk.demo.feature.repository.BookRepository;
import vn.com.dsk.demo.feature.repository.ProposalRepository;
import vn.com.dsk.demo.feature.service.ProposalService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;

    private final ModelMapper modelMapper;

    private final AccountRepository accountRepository;

    private final BookRepository bookRepository;

    @Override
    public void createProposal(ProposalRequest proposalRequest) {
        Proposal proposal = new Proposal();
        var account = getCurrentAccount();
        proposal.setAccount(account);
        List<BookProposal> listBookProposal = proposalRequest.getListBookRq().stream().map(
                        e -> {
                            BookProposal bookProposal = new BookProposal();

                            var book = bookRepository.findById(e.getBookId()).orElseThrow( ()
                                    -> new EntityNotFoundException(Book.class.getName(), e.getBookId().toString()));

                            bookProposal.setProposal(proposal);
                            bookProposal.setBook(book);
                            bookProposal.setQuantity(e.getQuantity());
                            return bookProposal;
                        })
                .toList();

        proposal.setBookProposals(listBookProposal);
        proposalRepository.save(proposal);
    }

    private Account getCurrentAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(Account.class.getName(), username));
    }
}
