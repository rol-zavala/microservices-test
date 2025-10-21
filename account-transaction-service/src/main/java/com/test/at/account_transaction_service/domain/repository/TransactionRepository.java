package com.test.at.account_transaction_service.domain.repository;

import com.test.at.account_transaction_service.domain.entity.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
     select t from Transaction t
     where t.account.customerCode = :customer
       and t.transactionDate between :from and :to
     order by t.transactionDate asc, t.id asc
  """)
    List<Transaction> findByCustomerAndDate(@Param("customer") String customerCode,
                                            @Param("from") LocalDate from,
                                            @Param("to") LocalDate to);
}
