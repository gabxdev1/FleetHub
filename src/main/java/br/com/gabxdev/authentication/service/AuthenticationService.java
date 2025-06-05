package br.com.gabxdev.authentication.service;

import br.com.gabxdev.authentication.mapper.SignupMapper;
import br.com.gabxdev.authentication.request.IamLoginRequest;
import br.com.gabxdev.authentication.request.LoginRequest;
import br.com.gabxdev.authentication.request.SignupRequest;
import br.com.gabxdev.authentication.response.TokenJwtResponse;
import br.com.gabxdev.core.properties.FleetHubProperties;
import br.com.gabxdev.core.security.JwtService;
import br.com.gabxdev.core.security.LoginType;
import br.com.gabxdev.iam.service.IamUserService;
import br.com.gabxdev.user.mapper.UserMapper;
import br.com.gabxdev.user.response.UserResponse;
import br.com.gabxdev.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;

    private final IamUserService iamUserService;

    private final PasswordEncoder passwordEncoder;

    private final SignupMapper signupMapper;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final FleetHubProperties fleetHubProperties;

    public UserResponse signup(SignupRequest signupRequest) {
        var user = signupMapper.toAccountUser(signupRequest);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var uerSaved = userService.save(user);

        return userMapper.toUserResponse(uerSaved);
    }

    public TokenJwtResponse loginAccountUser(LoginRequest request) {
        var accountUser = userService.findUserIdByEmailOrThrow(request.email().trim());

        validatePassword(request.password().trim(), accountUser.password());

        return createToken(accountUser.id(), LoginType.USER);
    }

    public TokenJwtResponse loginIamUser(IamLoginRequest request) {
        var iamUser = iamUserService.findAccountUserIdByEmailOrThrow(request.email().trim(), request.accountId());

        validatePassword(request.password().trim(), iamUser.password());

        return createToken(iamUser.id(), LoginType.IAM);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    private TokenJwtResponse createToken(Long userId, LoginType loginType) {
        var token = jwtService.generateToken(userId, loginType);

        var expirationSeconds = fleetHubProperties.getJwt().getExpirationSeconds();

        return TokenJwtResponse.builder()
                .accessToken(token)
                .expiresIn(expirationSeconds)
                .tokenType("Bearer")
                .build();
    }
}
