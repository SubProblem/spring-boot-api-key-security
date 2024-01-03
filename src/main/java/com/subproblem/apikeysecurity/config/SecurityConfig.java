package com.subproblem.apikeysecurity.config;


import com.subproblem.apikeysecurity.entity.Role;
import com.subproblem.apikeysecurity.jwt.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.subproblem.apikeysecurity.entity.Permission.*;
import static com.subproblem.apikeysecurity.entity.Role.USER;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final AuthFilter authFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(c -> c.disable())
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers("/api/v1/auth/**").permitAll()

                                        .requestMatchers("/api/v1/user/**").hasRole(USER.name())
                                        .requestMatchers(HttpMethod.GET, "/api/v1/user/**").hasAuthority(USER_READ.name())
                                        .requestMatchers(HttpMethod.POST, "/api/v1/user/**").hasAuthority(USER_CREATE.name())
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user/**").hasAuthority(USER_DELETE.name())
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/user/**").hasAuthority(USER_UPDATE.name())

                                        .requestMatchers("/api/v1/key/**").hasRole(USER.name())
                                        .requestMatchers(HttpMethod.GET, "/api/v1/key/**").hasAuthority(USER_READ.name())
                                        .requestMatchers(HttpMethod.POST, "/api/v1/key/**").hasAuthority(USER_CREATE.name())
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/key/**").hasAuthority(USER_DELETE.name())
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/key/**").hasAuthority(USER_UPDATE.name())

                                        .anyRequest()
                                        .authenticated()

                    )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
