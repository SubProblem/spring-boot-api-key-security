package com.subproblem.apikeysecurity.controller;

import com.subproblem.apikeysecurity.dto.request.ApiKeyRequestDto;
import com.subproblem.apikeysecurity.security.CustomUserDetails;
import com.subproblem.apikeysecurity.service.ApiKeyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/key")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @GetMapping("/generate")
    public ResponseEntity<?> generateKey(Authentication authentication) {

        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        return apiKeyService.generateApiKey(userDetails);

    }

    @GetMapping("/data")
    public ResponseEntity<?> getData(Authentication authentication, HttpServletRequest request) {

        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        return apiKeyService.accessData(request, userDetails);

    }

    @GetMapping("/keys")
    public ResponseEntity<?> getApiKey(Authentication authentication) {
        var userDetails = (CustomUserDetails) authentication.getPrincipal();
        return apiKeyService.getApiKey(userDetails.getUsername());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteApiKey(@RequestBody ApiKeyRequestDto requestDto) {
        return apiKeyService.deleteApiKey(requestDto.key());
    }
}
