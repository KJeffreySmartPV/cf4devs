package com.example.edgeservice;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EdgeServiceApplication {
    @Bean
    RestOperations restOperations() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EdgeServiceApplication.class, args);
    }
}

@RestController
class QuoteController {
    private final RestOperations restOperations;

    @Value("${vcap.services.quote-service.credentials.url}")
    private String quoteURI;

    QuoteController(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @GetMapping("/quote")
    public Quote getRandomQuote() {
        return this.restOperations.getForObject(quoteURI+"/random", Quote.class);
    }
}

@Data
@NoArgsConstructor
class Quote {
    public Long id;
    public String text, source;
}