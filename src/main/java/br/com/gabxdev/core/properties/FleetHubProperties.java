package br.com.gabxdev.core.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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

    @Valid
    private Iam iam;

    @Valid
    private ProjectManagement projectManagement;

    @Valid
    private FleetManagement fleetManagement;

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

    @Getter
    @Setter
    public static class Iam implements ServiceRegister {
        @NotNull
        @Min(1)
        private Long id;

        @NotBlank
        private String name;

        @NotBlank
        private String code;

        @NotBlank
        private String description;
    }

    @Getter
    @Setter
    public static class ProjectManagement implements ServiceRegister {
        @NotNull
        @Min(1)
        private Long id;

        @NotBlank
        private String name;

        @NotBlank
        private String code;

        @NotBlank
        private String description;
    }

    @Getter
    @Setter
    public static class FleetManagement implements ServiceRegister {
        @NotNull
        @Min(1)
        private Long id;

        @NotBlank
        private String name;

        @NotBlank
        private String code;

        @NotBlank
        private String description;
    }
}
