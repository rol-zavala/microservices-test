package com.test.at.customer_people_service.service.impl;

import com.test.at.customer_people_service.domain.entity.Customer;
import com.test.at.customer_people_service.domain.entity.Person;
import com.test.at.customer_people_service.domain.repository.CustomerRepository;
import com.test.at.customer_people_service.domain.repository.PersonRepository;
import com.test.at.customer_people_service.dto.CustomerRequestDto;
import com.test.at.customer_people_service.dto.CustomerResponseDto;
import com.test.at.customer_people_service.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepo;
    private final PersonRepository personRepo;

    @Transactional
    public CustomerResponseDto create(CustomerRequestDto req) {
        Person p = Person.builder()
                .name(req.getName()).gender(req.getGender()).age(req.getAge())
                .identification(req.getIdentification()).address(req.getAddress())
                .phone(req.getPhone()).build();
        p = personRepo.save(p);

        Customer c = Customer.builder()
                .person(p).customerCode(req.getCustomerCode())
                .password(req.getPassword()).status(req.getStatus())
                .build();
        c = customerRepo.save(c);

        return toResponse(c);
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() { return customerRepo.findAll(); }

    @Transactional(readOnly = true)
    public Customer findByCode(String code) {
        return customerRepo.findByCustomerCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Transactional
    public CustomerResponseDto update(String code, CustomerRequestDto req) {
        Customer c = findByCode(code);
        Person p = c.getPerson();
        p.setName(req.getName());
        p.setGender(req.getGender());
        p.setAge(req.getAge());
        p.setIdentification(req.getIdentification());
        p.setAddress(req.getAddress());
        p.setPhone(req.getPhone());
        c.setPassword(req.getPassword());
        c.setStatus(req.getStatus());
        return toResponse(c);
    }

    @Transactional
    public Customer delete(String code) {
        Customer c = findByCode(code);
        c.setStatus(false);
        return c;
    }

    private CustomerResponseDto toResponse(Customer c) {
        CustomerResponseDto r = new CustomerResponseDto();
        r.setCustomerCode(c.getCustomerCode());
        r.setName(c.getPerson().getName());
        r.setIdentification(c.getPerson().getIdentification());
        r.setStatus(c.getStatus());
        return r;
    }
}
