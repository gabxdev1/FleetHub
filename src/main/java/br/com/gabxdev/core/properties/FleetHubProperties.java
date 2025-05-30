package br.com.gabxdev.core.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "fleet-hub")
@Component
public class FleetHubProperties {

    @Valid
    private Jwt jwt;

    @Valid
    private Security security;

    @Getter
    @Setter
    public static class Jwt {
        @NotBlank
        private String secretKey;

        @NotNull
        private long expirationSeconds;
    }

    @Getter
    @Setter
    public static class Security {
        @NotBlank
        private String whitelist;

        public String[] getWhitelist() {
            return whitelist.split(",");
        }
    }
}
