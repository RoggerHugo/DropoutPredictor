package com.crai.ia.dropoutpredictor.dto;

import java.util.Map;

public record PredictRequest(Map<String, Object> features) {
}