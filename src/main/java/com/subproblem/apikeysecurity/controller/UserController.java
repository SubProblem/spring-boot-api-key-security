package com.subproblem.apikeysecurity.controller;

import com.subproblem.apikeysecurity.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @GetMapping
    public String hello(Authentication authentication) {

        var user = (CustomUserDetails) authentication.getPrincipal();
        return "Hello user: " + user.getUsername();
    }


}
