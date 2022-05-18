package ru.itis.shop.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.shop.models.Role;
import ru.itis.shop.security.UserDetailsImpl;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Profile("rest")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Bad token or expired");
        }

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .userId(Long.parseLong(claims.get("sub", String.class)))
                .roles((Set<Role>) claims.get("roles", ArrayList.class).stream().collect(Collectors.toSet()))
                .username(claims.get("username", String.class))
                .build();
        authentication.setAuthenticated(true);
        ((JwtAuthentication) authentication).setUserDetails(userDetails);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
