package com.test.at.account_transaction_service.controller;

import com.test.at.account_transaction_service.domain.entity.Account;
import com.test.at.account_transaction_service.dto.AccountRequestDto;
import com.test.at.account_transaction_service.exception.BusinessException;
import com.test.at.account_transaction_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody AccountRequestDto req){
        try {
            return ResponseEntity.ok(service.create(req));
        } catch (BusinessException ex) {
            throw new BusinessException("Account already exists");
        }
    }

    @GetMapping
    public ResponseEntity<List<Account>> all(){ return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{number}")
    public ResponseEntity<Account> byNumber(@PathVariable String number){
        return ResponseEntity.ok(service.findByNumber(number));
    }

    @PutMapping("/{number}")
    public ResponseEntity<Account> update(@PathVariable String number, @Valid @RequestBody AccountRequestDto req){

        return ResponseEntity.ok(service.update(number, req));

    }
}
