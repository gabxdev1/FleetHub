package br.com.gabxdev.authentication.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record IamLoginRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        UUID accountId
) {
}
