package com.test.at.account_transaction_service.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data @Builder
public class StatementItem {
    private LocalDate date;
    private String customer;
    private String accountNumber;
    private String type;
    private BigDecimal initialBalance;
    private Boolean status;
    private BigDecimal movement;
    private BigDecimal availableBalance;
}
