package com.brunoandreotti.course.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(Integer errorCode,
                            String errorMessage,
                            Map<String, String> errorDetails) {
}
