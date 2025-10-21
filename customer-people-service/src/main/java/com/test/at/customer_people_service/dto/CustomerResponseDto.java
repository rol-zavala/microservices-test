package com.test.at.customer_people_service.dto;

import lombok.Data;

@Data
public class CustomerResponseDto {
    private String customerCode;
    private String name;
    private String identification;
    private Boolean status;
}
