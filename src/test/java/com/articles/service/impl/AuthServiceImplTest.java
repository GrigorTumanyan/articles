package com.articles.service.impl;

import com.articles.dto.AuthenticationRequestDto;
import com.articles.dto.user.UserRequest;
import com.articles.dto.user.UserResponse;
import com.articles.enums.Role;
import com.articles.exception.ConflictException;
import com.articles.exception.RecordNotFoundException;
import com.articles.mapper.UserMapper;
import com.articles.model.User;
import com.articles.repository.UserRepository;
import com.articles.response.ErrorResponse;
import com.articles.response.Response;
import com.articles.security.JWTProvider;
import com.articles.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private JWTProvider jwtProvider;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MailService mailService;
    @InjectMocks
    private AuthServiceImpl authService;

    private AuthenticationRequestDto authenticationRequestDto;
    private User user;
    private UserResponse userResponse;
    private UserRequest userRequest;
    private HttpServletResponse servletResponse;

    @BeforeEach
    void setUp() {
        authenticationRequestDto = new AuthenticationRequestDto("C", "string");
        user = new User(1L, "A", "B", "C", "string", Role.USER, false, null);
        userResponse = UserMapper.userToUserDto(user);
        userRequest = new UserRequest("A", "B", "C");
        servletResponse = new MockHttpServletResponse();
    }

    @Test
    void login() {
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtProvider.createTokens(user.getEmail(), user.getRole())).thenReturn(List.of("Hi", "Bye"));
        Response<ErrorResponse, UserResponse> login = authService.login(authenticationRequestDto, servletResponse);
        assertEquals(userResponse, login.getSuccessObject());
    }

    @Test
    void registration() {
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("string");
        when(userRepository.save(any())).thenReturn(user);
        doNothing().when(mailService).sendMail("a", "a", "f");
        Response<ErrorResponse, UserResponse> registration = authService.registration(userRequest);
        assertEquals(userResponse, registration.getSuccessObject());
    }

    @Test
    void activateAccount() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Response<ErrorResponse, String> response = authService.activateAccount(1L);
        assertEquals("Your account has activated", response.getSuccessObject());
    }

    @Test
    void activateAccountNegativeCaseRecordNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> authService.activateAccount(1L));
    }

    @Test
    void activateAccountNegativeCaseConflictException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(null, null,
                null, null, null, null, true, null)));
        assertThrows(ConflictException.class, () -> authService.activateAccount(1L));

    }
}