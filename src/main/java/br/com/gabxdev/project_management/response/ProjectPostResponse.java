package br.com.gabxdev.project_management.response;

import java.time.Instant;
import java.util.UUID;

public record ProjectPostResponse(
        Long id,

        String name,

        String description,

        UUID accountId,

        Instant endDate
) {
}
