package br.com.gabxdev.core.audit;

import br.com.gabxdev.iam.domain.IamUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {

    private final static Long NEW_USER = 0L;

    @Override
    public Optional<Long> getCurrentAuditor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (isNewUser(auth)) {
            return Optional.of(NEW_USER);
        }

        var userAuthenticated = (IamUser) auth.getPrincipal();

        return Optional.of(userAuthenticated.getId());
    }

    private boolean isNewUser(Authentication auth) {
        return !(auth.getPrincipal() instanceof IamUser);
    }
}
