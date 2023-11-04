package vn.com.dsk.demo.feature.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;
import vn.com.dsk.demo.feature.dto.request.ExpenseRequest;
import vn.com.dsk.demo.feature.service.ExpenseService;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/private/expense/")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("expenses")
    public ResponseEntity<?> getAllExpenses() {
        return ResponseUtils.ok(expenseService.getAllExpenses());
    }

    @PostMapping("create-expense")
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest expenseRequest) {
        return ResponseUtils.ok(expenseService.createExpense(expenseRequest));
    }
}
