package com.test.at.account_transaction_service.controller;

import com.test.at.account_transaction_service.dto.StatementItem;
import com.test.at.account_transaction_service.service.StatementService;
import com.test.at.account_transaction_service.service.impl.StatementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final StatementService service;

    // /api/reports?customerCode=MM-001&from=2022-02-01&to=2022-02-28
    @GetMapping
    public ResponseEntity<List<StatementItem>> statement(
            @RequestParam String customerCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        return ResponseEntity.ok(service.statementByCustomerAndDate(customerCode, from, to));
    }
}
