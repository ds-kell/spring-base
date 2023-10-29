package vn.com.dsk.demo.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.demo.feature.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
