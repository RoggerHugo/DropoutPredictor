package com.crai.ia.dropoutpredictor.integration.crai;

import com.crai.ia.dropoutpredictor.dto.PredictRequest;
import com.crai.ia.dropoutpredictor.dto.PredictResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CraiClient {

  private final WebClient webClient;

  public CraiClient(WebClient.Builder builder, CraiProperties props) {
    this.webClient = builder
        .baseUrl(props.baseUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader("X-API-Key", props.apiKey())
        .build();
  }

  public PredictResponse predict(PredictRequest req) {
    System.out.println("Enviando solicitud de predicción a CRAI: " + req);
    return webClient.post()
        .uri("/v1/predict")
        .bodyValue(req)
        .retrieve()
        .bodyToMono(PredictResponse.class)
        .onErrorResume(ex -> {
          return Mono.just(new PredictResponse(null, null, null, null, null));
        })
        .block();
  }
}
