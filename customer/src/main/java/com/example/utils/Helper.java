package com.example.utils;

import com.example.model.Order;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

public class Helper {

    WebClient webClient = WebClient.create();
    public String callApiToRetrieve(String apiUrl){
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void callApiToPost(String apiUrl, Order payload){
         webClient.post()
                .uri(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchangeToMono(cr -> cr.bodyToMono(String.class))
                .timeout(Duration.ofMillis(10000))
                .block();
    }
}
