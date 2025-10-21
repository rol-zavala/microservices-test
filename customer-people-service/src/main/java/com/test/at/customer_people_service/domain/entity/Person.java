package com.test.at.customer_people_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person", schema = "sch_people")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Person {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    @Column(unique = true, nullable = false)
    private String identification;
    private String address;
    private String phone;
}
