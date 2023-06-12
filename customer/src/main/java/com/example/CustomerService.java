package com.example;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    public void registerCustomer(CustomerRequestRegistration request) {
        Customer customer = Customer.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .build();
        // todo: check if email is valid
        // todo: check if email is not taken

        customerRepository.saveAndFlush(customer);
        // todo: check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud_check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }

        // save customer to database
        customerRepository.save(customer);
    }
}
