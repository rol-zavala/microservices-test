package com.test.at.account_transaction_service.service;

import com.test.at.account_transaction_service.domain.entity.Account;
import com.test.at.account_transaction_service.dto.AccountRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    @Transactional
    Account create(AccountRequestDto req);

    Account findByNumber(String number);

    @Transactional
    Account update(String number, AccountRequestDto req);

}
