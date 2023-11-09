package vn.com.dsk.demo.feature.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.base.exception.EntityNotFoundException;
import vn.com.dsk.demo.base.model.User;
import vn.com.dsk.demo.base.repository.UserRepository;
import vn.com.dsk.demo.feature.dto.ExpenseDto;
import vn.com.dsk.demo.feature.dto.request.ExpenseRequest;
import vn.com.dsk.demo.feature.model.Expense;
import vn.com.dsk.demo.feature.repository.ExpenseRepository;
import vn.com.dsk.demo.feature.service.ExpenseService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Override
    public String createExpense(ExpenseRequest expenseRequest) {
        Expense expense = modelMapper.map(expenseRequest, Expense.class);
        var user = getCurrentUser();
        expense.setBranch(user.getBranch());
        expense.setDate(new Date(System.currentTimeMillis()));
        expense.setUser(getCurrentUser());
        expenseRepository.save(expense);
        return "Expense have been created";
    }


    @Override
    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository.findAll().stream().map(
                e -> modelMapper.map(e, ExpenseDto.class)
        ).collect(Collectors.toList());
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getName(), username));
    }
}
