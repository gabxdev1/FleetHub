package br.com.gabxdev.project_management.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ProjectPostRequest(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull
        @FutureOrPresent
        Instant endDate
) {
}
