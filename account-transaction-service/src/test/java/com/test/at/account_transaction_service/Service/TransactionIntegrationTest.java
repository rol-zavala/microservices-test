package com.test.at.account_transaction_service.Service;

import com.test.at.account_transaction_service.domain.entity.Account;
import com.test.at.account_transaction_service.domain.repository.AccountRepository;
import com.test.at.account_transaction_service.domain.repository.TransactionRepository;
import com.test.at.account_transaction_service.dto.TransactionRequestDto;
import com.test.at.account_transaction_service.exception.BusinessException;
import com.test.at.account_transaction_service.service.TransactionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TransactionIntegrationTest {

    @Autowired private TransactionService txService;
    @Autowired private AccountRepository accountRepo;
    @Autowired private TransactionRepository txRepo;

    private static final String ACC = "TEST-001";

    @BeforeEach
    void setup() {
        // Creamos una cuenta con saldo inicial 100
        var acc = Account.builder()
                .accountNumber(ACC)
                .accountType("Savings")
                .initialBalance(new BigDecimal("100"))
                .availableBalance(new BigDecimal("100"))
                .status(true)
                .customerCode("CU-TEST")
                .build();
        accountRepo.save(acc);
    }

    @Test
    void withdrawal_below_zero_should_throw_business_exception() {
        var req = new TransactionRequestDto();
        req.setAccountNumber(ACC);
        req.setTransactionDate(LocalDate.of(2022, 2, 10));
        req.setTransactionType("Withdrawal");
        req.setAmount(new BigDecimal("150"));

        assertThatThrownBy(() -> txService.register(req))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Saldo no disponible");

        // No se registró movimiento
        assertThat(txRepo.count()).isZero();

        // Saldo de cuenta intacto
        var acc = accountRepo.findByAccountNumber(ACC).orElseThrow();
        assertThat(acc.getAvailableBalance()).isEqualByComparingTo("100");
    }

    @Test
    void deposit_should_increase_balance_and_persist_transaction() {
        var req = new TransactionRequestDto();
        req.setAccountNumber(ACC);
        req.setTransactionDate(LocalDate.of(2022, 2, 10));
        req.setTransactionType("Deposit");
        req.setAmount(new BigDecimal("600"));

        var tx = txService.register(req);

        assertThat(tx.getBalance()).isEqualByComparingTo("700");
        assertThat(txRepo.count()).isEqualTo(1);

        var acc = accountRepo.findByAccountNumber(ACC).orElseThrow();
        assertThat(acc.getAvailableBalance()).isEqualByComparingTo("700");
    }
}
