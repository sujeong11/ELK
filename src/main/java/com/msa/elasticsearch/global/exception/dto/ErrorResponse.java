package com.msa.elasticsearch.global.exception.dto;

public record ErrorResponse(
    Integer code,
    String message
) {
}
