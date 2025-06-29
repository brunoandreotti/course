package com.brunoandreotti.course.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubscriptionRecordDTO(@NotNull(message = "UserId is mandatory!") UUID userId) {
}
