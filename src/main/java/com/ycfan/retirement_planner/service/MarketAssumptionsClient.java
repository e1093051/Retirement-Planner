package com.ycfan.retirement_planner.service;

import com.ycfan.retirement_planner.model.dto.ReturnVolatilityEstimate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MarketAssumptionsClient {

    private final WebClient webClient;

    public MarketAssumptionsClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8000").build();
    }

    public ReturnVolatilityEstimate estimate(String symbols, String weights, String period) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/estimate")
                        .queryParam("symbols", symbols)
                        .queryParam("weights", weights)
                        .queryParam("period", period)
                        .build())
                .retrieve()
                .bodyToMono(ReturnVolatilityEstimate.class)
                .block(); // prototype OK; later can make async
    }
}
