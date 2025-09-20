package com.crai.ia.dropoutpredictor.dto;

public record AlumnoDetailWithPredictionResponse(
        AlumnoDetailResponse detail,
        PredictResponse prediction) {
}