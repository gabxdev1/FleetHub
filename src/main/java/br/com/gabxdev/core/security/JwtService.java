package br.com.gabxdev.core.security;

import br.com.gabxdev.core.properties.FleetHubProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final FleetHubProperties fleetHubProperties;

    public String generateToken(Long userId) {
        var now = Instant.now();
        var expirationSeconds = fleetHubProperties.getJwt().getExpirationSeconds();
        var expirationDate = Date.from(now.plusSeconds(expirationSeconds));

        return Jwts.builder()
                .subject(userId.toString())
                .issuer("https://gabxdev.gabxdev.com")
                .audience().add("FleetHub")
                .and()
                .issuedAt(Date.from(now))
                .expiration(expirationDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public Long extractUserIdAndValidate(String token) {
        var userId = Jwts.parser()
                .verifyWith(getSigningKey())
                .requireAudience("FleetHub")
                .requireIssuer("https://gabxdev.gabxdev.com")
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return Long.parseLong(userId);
    }

    private SecretKey getSigningKey() {
        var keyBytes = Decoders.BASE64.decode(fleetHubProperties.getJwt().getSecretKey());

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
