package br.com.gabxdev.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<?> signupAccountUser() {

    }

    @PostMapping("/login")
    public ResponseEntity<> LoginAccountUser() {

    }

    @PostMapping("/login/iam-user")
    public ResponseEntity<> LoginIamUser() {

    }
}
