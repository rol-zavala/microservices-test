package com.test.at.customer_people_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerRequestDto {
    @NotBlank private String name;
    @NotBlank private String gender;
    @NotNull  private Integer age;
    @NotBlank private String identification;
    @NotBlank private String address;
    @NotBlank private String phone;

    @NotBlank private String customerCode;
    @NotBlank private String password;
    @NotNull  private Boolean status;
}

