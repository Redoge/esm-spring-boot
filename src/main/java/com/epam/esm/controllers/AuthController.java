package com.epam.esm.controllers;

import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.pojo.AuthenticationResponse;
import com.epam.esm.pojo.RegisterRequest;
import com.epam.esm.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws ObjectIsExistException {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.auth(request));
    }
}
