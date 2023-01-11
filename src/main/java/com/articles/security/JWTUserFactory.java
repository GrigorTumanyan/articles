package com.articles.security;

import com.articles.enums.Role;
import com.articles.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public final class JWTUserFactory {
    private JWTUserFactory(){

    }

    public static JWTUser create(User user) {
        return new JWTUser(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getIsActive().equals(true),
                createGrantedAuthorityList(user.getRole()));
    }

    private static List<GrantedAuthority> createGrantedAuthorityList(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
}
