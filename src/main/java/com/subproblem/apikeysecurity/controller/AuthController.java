package com.subproblem.apikeysecurity.controller;



import com.subproblem.apikeysecurity.dto.request.AuthenticationRequestDto;
import com.subproblem.apikeysecurity.dto.request.AuthorizationRequestDto;
import com.subproblem.apikeysecurity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthorizationRequestDto requestDto) {
        return authService.registerUser(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
        return authService.authenticateUser(requestDto);
    }
    
}
