package com.test.at.customer_people_service.service;

import com.test.at.customer_people_service.domain.entity.Customer;
import com.test.at.customer_people_service.dto.CustomerRequestDto;
import com.test.at.customer_people_service.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto create(CustomerRequestDto req);
    List<Customer> findAll();
    Customer findByCode(String code);
    CustomerResponseDto update(String code, CustomerRequestDto req);
    Customer delete(String code);
}
