package com.ps.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Created by samchu on 2017/4/5.
 */
public final class AuthenticationUtil {
    public static void configureAuthentication(String role) {
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "user",
                role,
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void clearAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
