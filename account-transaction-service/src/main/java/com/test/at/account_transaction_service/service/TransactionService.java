package com.test.at.account_transaction_service.service;

import com.test.at.account_transaction_service.domain.entity.Transaction;
import com.test.at.account_transaction_service.dto.TransactionRequestDto;

public interface TransactionService {
    Transaction register(TransactionRequestDto req);
}
