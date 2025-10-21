package com.test.at.account_transaction_service.service.impl;

import com.test.at.account_transaction_service.domain.entity.Account;
import com.test.at.account_transaction_service.domain.entity.Transaction;
import com.test.at.account_transaction_service.domain.repository.AccountRepository;
import com.test.at.account_transaction_service.domain.repository.TransactionRepository;
import com.test.at.account_transaction_service.dto.TransactionRequestDto;
import com.test.at.account_transaction_service.exception.BusinessException;
import com.test.at.account_transaction_service.exception.NotFoundException;
import com.test.at.account_transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service @RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    @Override @Transactional
    public Transaction register(TransactionRequestDto req) {
        Account acc = accountRepo.findByAccountNumber(req.getAccountNumber())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        BigDecimal amount = req.getAmount().abs();
        String type = req.getTransactionType();

        BigDecimal newBalance = switch (type) {
            case "Deposit" -> acc.getAvailableBalance().add(amount);
            case "Withdrawal" -> {
                BigDecimal s = acc.getAvailableBalance().subtract(amount);
                if (s.signum() < 0) throw new BusinessException("Saldo no disponible");
                yield s;
            }
            default -> throw new IllegalArgumentException("transactionType must be Deposit or Withdrawal");
        };

        acc.setAvailableBalance(newBalance);

        Transaction tx = Transaction.builder()
                .transactionDate(req.getTransactionDate())
                .transactionType(type)
                .amount(amount)
                .balance(newBalance)
                .account(acc)
                .build();

        return txRepo.save(tx);
    }
}
