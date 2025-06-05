package br.com.gabxdev.core.audit;

import br.com.gabxdev.core.common.UserIdentity;
import br.com.gabxdev.iam.domain.IamUser;
import br.com.gabxdev.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final static String NEW_USER = "NEW_USER";

    @Override
    public Optional<String> getCurrentAuditor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (isNewUser(auth)) {
            return Optional.of(NEW_USER);
        }

        var userAuthenticated = (UserIdentity) auth.getPrincipal();

        return Optional.of(userAuthenticated.getEmail());
    }

    private boolean isNewUser(Authentication auth) {
        return !(auth.getPrincipal() instanceof IamUser || auth.getPrincipal() instanceof User);
    }
}
