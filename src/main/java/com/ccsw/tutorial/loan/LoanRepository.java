package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LoanRepository extends PagingAndSortingRepository<Loan, Long>, CrudRepository<Loan, Long> {

    @Query("SELECT l FROM Loan l WHERE " +
            "(:gameId IS NULL OR l.game.id = :gameId) AND " +
            "(:customerId IS NULL OR l.customer.id = :customerId) AND " +
            "(:date IS NULL OR (l.loanStart <= :date AND l.loanEnd >= :date))")
    Page<Loan> findWithFilters(@Param("gameId") Long gameId,
                               @Param("customerId") Long customerId,
                               @Param("date") Date date,
                               Pageable pageable);
}