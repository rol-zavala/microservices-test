package com.test.at.account_transaction_service.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PeopleClient {

    private final RestClient client;

    public PeopleClient(@Value("${people.url}") String baseUrl) {
        this.client = RestClient.builder().baseUrl(baseUrl).build();
    }

    public PeopleCustomerDTO getByCode(String code) {
        ResponseEntity<PeopleCustomerDTO> res = client.get()
                .uri("/api/customers/{code}", code)
                .retrieve()
                .toEntity(PeopleCustomerDTO.class);
        return res.getBody();
    }

    public PeopleCustomerDTO getByName(String name) {
        ResponseEntity<PeopleCustomerDTO> res = client.get()
                .uri(uriBuilder -> uriBuilder.path("/api/customers")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .toEntity(PeopleCustomerDTO.class);
        return res.getBody();
    }

    public record PeopleCustomerDTO(String customerCode, String name, String identification, Boolean status) {}
}
