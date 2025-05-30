package br.com.gabxdev.core.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl", dateTimeProviderRef = "dateTimeProviderCustom")
public class AuditConfig {
}
