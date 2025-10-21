package com.test.at.customer_people_service.controller;

import com.test.at.customer_people_service.domain.entity.Customer;
import com.test.at.customer_people_service.dto.CustomerRequestDto;
import com.test.at.customer_people_service.dto.CustomerResponseDto;
import com.test.at.customer_people_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(@Valid @RequestBody CustomerRequestDto req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Customer> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable String code,
                                                   @Valid @RequestBody CustomerRequestDto req) {
        return ResponseEntity.ok(service.update(code, req));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Customer> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.ok(service.delete(code));
    }
}
