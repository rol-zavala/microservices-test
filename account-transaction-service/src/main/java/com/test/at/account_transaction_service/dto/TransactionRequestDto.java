package com.test.at.account_transaction_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequestDto {
    @NotBlank private String accountNumber;
    @NotNull  private LocalDate transactionDate;
    @NotBlank private String transactionType;
    @NotNull  @Positive private BigDecimal amount;
}
