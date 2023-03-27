package com.example.tokens.services;

import com.example.tokens.dtos.AuthenticationRequest;
import com.example.tokens.dtos.AuthenticationResponse;
import com.example.tokens.dtos.RegisterRequest;
import com.example.tokens.enums.Role;
import com.example.tokens.model.User;
import com.example.tokens.rdbms.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User
                .builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.User)
                .build();
        userRepository.save(user);
        Map<String, Object> roleMap = getRoleM(user);

        var jwtToken = jwtService.generateToken(roleMap, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Map<String, Object> roleMap = getRoleM(user);
        var jwtToken = jwtService.generateToken(roleMap, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private Map<String, Object> getRoleM(User user) {
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("Role", user.getRole());
        return roleMap;
    }
}
