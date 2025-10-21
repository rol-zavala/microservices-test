package com.test.at.account_transaction_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequestDto{
    @NotBlank private String accountNumber;
    @NotBlank private String accountType;
    @NotNull  private BigDecimal initialBalance;
    @NotBlank private String customerCode;
}
