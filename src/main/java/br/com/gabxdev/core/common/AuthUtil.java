package br.com.gabxdev.core.common;

import br.com.gabxdev.iam.repository.UserRepository;
import br.com.gabxdev.core.security.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public Authentication getAuthFromRequestAndValidate(HttpServletRequest request) {
        var tokenFromRequest = getTokenFromRequest(request);

        var userId = jwtService.extractUserIdAndValidate(tokenFromRequest);

        var auth = createAuthentication(userId);

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

    private UsernamePasswordAuthenticationToken createAuthentication(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User %d not found".formatted(userId)));

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
