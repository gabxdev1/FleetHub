package br.com.gabxdev.iam.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record IamUserPostRequest(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        List<ServiceCatalogPermPostRequest> serviceCatalogPerm
) {
}
