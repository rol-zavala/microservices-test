package com.test.at.account_transaction_service.service.impl;

import com.test.at.account_transaction_service.domain.entity.Account;
import com.test.at.account_transaction_service.domain.repository.AccountRepository;
import com.test.at.account_transaction_service.dto.AccountRequestDto;
import com.test.at.account_transaction_service.exception.NotFoundException;
import com.test.at.account_transaction_service.integration.PeopleClient;
import com.test.at.account_transaction_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repo;
    private final PeopleClient people;

    @Transactional
    @Override
    public Account create(AccountRequestDto req){
        var res = people.getByCode(req.getCustomerCode());
        if (res == null || Boolean.FALSE.equals(res.status()))
            throw new NotFoundException("Customer not found or inactive");

        var acc = Account.builder()
                .accountNumber(req.getAccountNumber())
                .accountType(req.getAccountType())
                .initialBalance(req.getInitialBalance())
                .availableBalance(req.getInitialBalance())
                .status(true)
                .customerCode(req.getCustomerCode())
                .build();
        return repo.save(acc);
    }

    @Transactional(readOnly = true)
    @Override
    public Account findByNumber(String number){
        return repo.findByAccountNumber(number)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Account> findAll(){ return repo.findAll(); }

    @Transactional
    public Account update(String number, AccountRequestDto req) {
        Account a = findByNumber(number);
        a.setAccountNumber(req.getAccountNumber());
        a.setAccountType(req.getAccountType());
        a.setInitialBalance(req.getInitialBalance());
        a.setAvailableBalance(req.getInitialBalance());
        a.setCustomerCode(req.getCustomerCode());
        return a;
    }
}
