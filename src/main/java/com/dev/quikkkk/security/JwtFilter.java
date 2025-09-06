package com.dev.quikkkk.security;

import com.dev.quikkkk.service.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final IJwtService service;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth/login") ||
                request.getServletPath().equals("/api/v1/auth/register") ||
                request.getServletPath().equals("/api/v1/auth/refresh")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = service.extractUsername(jwt);
        String userId = service.extractUserId(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (service.isTokenValid(jwt, username)) {
                List<String> roles = service.extractRoles(jwt);
                List<SimpleGrantedAuthority> authorities = (roles != null) ?
                        roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList() :
                        List.of();

                UserPrincipal principal = new UserPrincipal(
                        userId,
                        username,
                        authorities
                );

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        authorities
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
