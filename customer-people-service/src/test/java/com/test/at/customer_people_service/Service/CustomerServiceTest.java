package com.test.at.customer_people_service.Service;

import com.test.at.customer_people_service.domain.entity.Customer;
import com.test.at.customer_people_service.domain.repository.CustomerRepository;
import com.test.at.customer_people_service.domain.repository.PersonRepository;
import com.test.at.customer_people_service.dto.CustomerRequestDto;
import com.test.at.customer_people_service.service.CustomerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CustomerService.class)
class CustomerServiceTest {

    @Autowired private CustomerService service;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private PersonRepository personRepository;

    @Test
    void should_create_customer_and_link_person_one_to_one() {
        // given
        var req = new CustomerRequestDto();
        req.setName("Test User");
        req.setGender("F");
        req.setAge(28);
        req.setIdentification("ID-TU-001");
        req.setAddress("Main St 123");
        req.setPhone("999-111");
        req.setCustomerCode("TU-001");
        req.setPassword("secret");
        req.setStatus(true);

        // when
        var resp = service.create(req);

        // then
        assertThat(resp.getCustomerCode()).isEqualTo("TU-001");
        assertThat(resp.getName()).isEqualTo("Test User");
        assertThat(resp.getIdentification()).isEqualTo("ID-TU-001");
        assertThat(resp.getStatus()).isTrue();

        // and: persisted entities
        Customer c = customerRepository.findByCustomerCode("TU-001").orElseThrow();
        assertThat(c.getPerson().getName()).isEqualTo("Test User");
        assertThat(personRepository.count()).isEqualTo(1);
        assertThat(customerRepository.count()).isEqualTo(1);
    }
}
