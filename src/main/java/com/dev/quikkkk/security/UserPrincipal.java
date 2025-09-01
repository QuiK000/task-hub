package com.dev.quikkkk.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserPrincipal(
        String id,
        String username,
        Collection<? extends GrantedAuthority> authorities
) {
}
