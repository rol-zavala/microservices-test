package com.test.at.customer_people_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer", schema = "sch_people")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    private Person person;

    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

    private String password;

    private Boolean status = true;
}
