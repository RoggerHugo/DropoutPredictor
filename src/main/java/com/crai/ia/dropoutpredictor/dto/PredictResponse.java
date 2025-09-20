package com.crai.ia.dropoutpredictor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record PredictResponse(
        @JsonProperty("prediction_label") String predictionLabel,
        Double score,
        @JsonProperty("model_used") String modelUsed,
        @JsonProperty("model_version") String modelVersion,
        Map<String, Object> models) {
}