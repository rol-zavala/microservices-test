package com.test.at.account_transaction_service.controller;

import com.test.at.account_transaction_service.domain.entity.Transaction;
import com.test.at.account_transaction_service.dto.TransactionRequestDto;
import com.test.at.account_transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> register(@Valid @RequestBody TransactionRequestDto req){
        return ResponseEntity.ok(service.register(req));
    }
}
