package com.example.util;

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

}
