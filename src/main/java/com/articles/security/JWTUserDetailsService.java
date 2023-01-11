package com.articles.security;

import com.articles.exception.RecordNotFoundException;
import com.articles.model.User;
import com.articles.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new RecordNotFoundException("Email : " + email + " is not found"));
        return JWTUserFactory.create(user);
    }
}
