package com.test.at.account_transaction_service.service.impl;

import com.test.at.account_transaction_service.dto.StatementItem;
import com.test.at.account_transaction_service.integration.PeopleClient;
import com.test.at.account_transaction_service.domain.repository.TransactionRepository;
import com.test.at.account_transaction_service.service.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service @RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final TransactionRepository txRepo;
    private final PeopleClient people;

    @Override
    @Transactional(readOnly = true)
    public List<StatementItem> statementByCustomerAndDate(String customerCode, LocalDate from, LocalDate to) {
        var customer = people.getByCode(customerCode);
        String name = customer != null ? customer.name() : customerCode;

        var txs = txRepo.findByCustomerAndDate(customerCode, from, to);
        List<StatementItem> items = new ArrayList<>();

        txs.forEach(t -> {
            var a = t.getAccount();
            BigDecimal mov = "Withdrawal".equals(t.getTransactionType())
                    ? t.getAmount().negate()
                    : t.getAmount();

            items.add(StatementItem.builder()
                    .date(t.getTransactionDate())
                    .customer(name)
                    .accountNumber(a.getAccountNumber())
                    .type(a.getAccountType())
                    .initialBalance(a.getInitialBalance())
                    .status(a.getStatus())
                    .movement(mov)
                    .availableBalance(t.getBalance())
                    .build());
        });
        return items;
    }
}
