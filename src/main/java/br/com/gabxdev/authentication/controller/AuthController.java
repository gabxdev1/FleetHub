package br.com.gabxdev.authentication.controller;

import br.com.gabxdev.authentication.request.IamLoginRequest;
import br.com.gabxdev.authentication.request.LoginRequest;
import br.com.gabxdev.authentication.request.SignupRequest;
import br.com.gabxdev.authentication.response.TokenJwtResponse;
import br.com.gabxdev.authentication.service.AuthenticationService;
import br.com.gabxdev.core.common.HeaderUtils;
import br.com.gabxdev.user.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    private final HeaderUtils headerUtils;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signupUser(@Valid @RequestBody SignupRequest request) {
        var accountUserResponse = authenticationService.signup(request);

        var uriLocation = headerUtils.createHeaderLocation(accountUserResponse.userId().toString());

        return ResponseEntity.created(uriLocation).body(accountUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenJwtResponse> LoginUser(@Valid @RequestBody LoginRequest request) {
        var tokenJwtResponse = authenticationService.loginAccountUser(request);

        return ResponseEntity.ok(tokenJwtResponse);
    }

    @PostMapping("/login/iam-user")
    public ResponseEntity<TokenJwtResponse> LoginIamUser(@Valid @RequestBody IamLoginRequest request) {
        var tokenJwtResponse = authenticationService.loginIamUser(request);

        return ResponseEntity.ok(tokenJwtResponse);
    }
}
