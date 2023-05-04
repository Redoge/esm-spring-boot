package com.epam.esm.services;

import com.epam.esm.entities.User;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.pojo.AuthenticationResponse;
import com.epam.esm.pojo.RegisterRequest;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.util.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.esm.util.StringConst.USER;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws ObjectIsExistException {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .money(BigDecimal.valueOf(0))
                .orders(List.of())
                .build();
        if(repository.existsByUsername(user.getUsername()))
            throw new ObjectIsExistException(USER, user.getUsername());
        repository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse auth(RegisterRequest request) {
        System.out.println("1");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword()));
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        System.out.println("2");
        var jwt = jwtService.generateToken(user);
        System.out.println("3");
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}
