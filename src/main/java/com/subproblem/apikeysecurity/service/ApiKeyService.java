package com.subproblem.apikeysecurity.service;

import com.subproblem.apikeysecurity.entity.ApiKey;
import com.subproblem.apikeysecurity.repository.ApiKeyRepository;
import com.subproblem.apikeysecurity.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final UserRepository userRepository;


    public ResponseEntity<?> accessData(HttpServletRequest request, UserDetails userDetails) {

        // Get api-key header

        String keyHeader = request.getHeader("api-key");

        if (!validateApiKey(keyHeader, userDetails.getUsername())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Successfully access mock endpoint with api-key");

    }

    public ResponseEntity<?> generateApiKey(UserDetails userDetails) {

        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String key = generateUniqueApiKey();

        var apiKey = ApiKey.builder()
                .key(key)
                .user(user)
                .creationDate(LocalDateTime.now())
                .enabled(true)
                .build();

        apiKeyRepository.save(apiKey);

        return ResponseEntity.ok(key);
    }

    public ResponseEntity<?> getApiKey(String email) {

        var apiKey = apiKeyRepository.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Key not found"));

        return ResponseEntity.ok(apiKey.getKey());
    }


    public ResponseEntity<?> deleteApiKey(String key) {

        var apiKey = apiKeyRepository.findByKey(key)
                .orElseThrow(() -> new NoSuchElementException("Key not found"));

        if (!apiKey.getKey().equals(key)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        apiKeyRepository.delete(apiKey);

        return ResponseEntity.ok("api-key is deleted");
    }


    private String generateUniqueApiKey() {
        return UUID.randomUUID().toString();
    }

    private boolean validateApiKey(String key, String email) {

        if (key == null || key == "") {
            return false;
        }

        var retrievedKey = apiKeyRepository.findByUserEmail(email)
                .orElseThrow(() -> new NoSuchElementException("wrong api-key"));

        if (!retrievedKey.getEnabled()) {
            return false;
        }

        return key.equals(retrievedKey.getKey());
    }
}
