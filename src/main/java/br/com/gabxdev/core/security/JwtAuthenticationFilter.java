package br.com.gabxdev.core.security;

import br.com.gabxdev.core.common.AuthUtil;
import br.com.gabxdev.core.properties.FleetHubProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;

    private final FleetHubProperties fleetHubProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            var auth = authUtil.getAuthFromRequestAndValidate(request);

            SecurityContextHolder.getContext().setAuthentication(auth);

            log.debug("Successfully authenticated user");

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage(), e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        var requestURI = request.getRequestURI();

        for (String whiteListUri : fleetHubProperties.getSecurity().getWhitelist()) {
            if (requestURI.equals(whiteListUri)) {
                return true;
            }
        }

        return false;
    }
}
