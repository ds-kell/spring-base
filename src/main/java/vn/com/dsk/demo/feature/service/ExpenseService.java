package vn.com.dsk.demo.feature.service;

import vn.com.dsk.demo.feature.dto.ExpenseDto;
import vn.com.dsk.demo.feature.dto.request.ExpenseRequest;

import java.util.List;

public interface ExpenseService {

    String createExpense(ExpenseRequest expenseRequest);

    List<ExpenseDto> getAllExpenses();
}
