package com.test.at.account_transaction_service.service;

import com.test.at.account_transaction_service.dto.StatementItem;

import java.time.LocalDate;
import java.util.List;

public interface StatementService {
    List<StatementItem> statementByCustomerAndDate(String customerCode, LocalDate from, LocalDate to);
}
