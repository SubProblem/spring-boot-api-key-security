package com.subproblem.apikeysecurity.dto.request;

public record AuthorizationRequestDto(
        String firstname,
        String lastname,
        String email,
        String password,
        String role
) {
}
