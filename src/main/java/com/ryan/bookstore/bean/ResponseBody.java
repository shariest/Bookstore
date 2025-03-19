package com.ryan.bookstore.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody {
    @Schema(description = "응답 코드", example = "200")
    private int statusCode;

    @Builder.Default
    @Schema(description = "응답 시간", example = "2025-01-01T00:00:00.000Z")
    private OffsetDateTime dt = OffsetDateTime.now();

    @Schema(description = "에러 메시지", example = "")
    private String message;
}