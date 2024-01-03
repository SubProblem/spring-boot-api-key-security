package com.subproblem.apikeysecurity.service;


import com.subproblem.apikeysecurity.dto.request.AuthenticationRequestDto;
import com.subproblem.apikeysecurity.dto.request.AuthorizationRequestDto;
import com.subproblem.apikeysecurity.entity.Role;
import com.subproblem.apikeysecurity.entity.User;
import com.subproblem.apikeysecurity.jwt.JwtService;
import com.subproblem.apikeysecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public ResponseEntity<?> registerUser(AuthorizationRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.email())) {
            return ResponseEntity.badRequest().body("incorrect email");
        }

        var user = User.builder()
                .firstname(requestDto.firstname())
                .lastname(requestDto.lastname())
                .email(requestDto.email())
                .password(passwordEncoder.encode(requestDto.password()))
                .role(Role.valueOf(requestDto.role().toUpperCase()))
                .build();

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> authenticateUser(AuthenticationRequestDto requestDto) {

        String email = requestDto.email();
        String password = requestDto.password();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var jwtToken = jwtService.generateJwtToken(email);

        return ResponseEntity.ok(jwtToken);
    }
}
