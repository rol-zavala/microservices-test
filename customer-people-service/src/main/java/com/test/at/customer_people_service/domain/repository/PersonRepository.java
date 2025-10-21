package com.test.at.customer_people_service.domain.repository;

import com.test.at.customer_people_service.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
