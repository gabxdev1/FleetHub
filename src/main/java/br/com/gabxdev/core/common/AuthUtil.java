package br.com.gabxdev.core.common;

import br.com.gabxdev.core.exception.ForbiddenException;
import br.com.gabxdev.core.security.JwtService;
import br.com.gabxdev.core.security.LoginType;
import br.com.gabxdev.iam.domain.IamUser;
import br.com.gabxdev.iam.repository.IamUserRepository;
import br.com.gabxdev.user.domain.User;
import br.com.gabxdev.user.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final IamUserRepository iamUserRepository;

    public UserIdentity getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof User || auth.getPrincipal() instanceof IamUser)) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }

        return (UserIdentity) auth.getPrincipal();
    }

    public Authentication getAuthFromRequestAndValidate(HttpServletRequest request) {
        var tokenFromRequest = getTokenFromRequest(request);

        var userId = jwtService.extractUserIdAndValidate(tokenFromRequest);
        var loginType = jwtService.extractLoginTypeAndValidate(tokenFromRequest);

        var auth = createAuthentication(userId, loginType);

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return auth;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null) {
            throw new JwtException("Invalid JWT token");
        }

        return getTokenFromHeaderAuthorization(authHeader);
    }

    private String getTokenFromHeaderAuthorization(String header) {
        if (!(header.startsWith(BEARER_PREFIX) && !header.substring(7).isEmpty())) {
            throw new JwtException("Invalid token.");
        }

        return header.substring(7);
    }

    private UsernamePasswordAuthenticationToken createAuthentication(Long userId, LoginType loginType) {
        if (loginType.equals(LoginType.USER)) {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User %d not found".formatted(userId)));

            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }

        if (loginType.equals(LoginType.IAM)) {
            var user = iamUserRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User %d not found".formatted(userId)));

            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }

        throw new ForbiddenException("Unsupported login type");
    }
}
