package com.subproblem.apikeysecurity.dto.request;

public record AuthenticationRequestDto(
        String email,
        String password
) {
}
