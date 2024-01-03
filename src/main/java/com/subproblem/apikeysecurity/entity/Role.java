package com.subproblem.apikeysecurity.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.subproblem.apikeysecurity.entity.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(Set.of(
            USER_CREATE,
            USER_READ,
            USER_UPDATE,
            USER_DELETE
        )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = new java.util.ArrayList<>(getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
