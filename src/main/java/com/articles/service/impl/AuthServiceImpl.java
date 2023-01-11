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
import com.articles.service.AuthService;
import com.articles.service.MailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JWTProvider jwtProvider;

    private final UserRepository userRepository;

    private final MailService mailService;

    @Override
    public Response<ErrorResponse, UserResponse> login(AuthenticationRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("Email : " + email + "is not found"));
        setTokens(response, email, user);
        return new Response<>(null, UserMapper.userToUserDto(user));
    }

    @Override
    public Response<ErrorResponse, UserResponse> registration(UserRequest userRequest) {
        User user = UserMapper.userRegisterToUser(userRequest);
        String password = generateRandomPassword();
        user.setRole(Role.USER);
        user.setIsActive(false);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        mailService.sendMail(user.getEmail(), "Account activation", generateActivationLink(password, savedUser.getId()));
        return new Response<>(null, UserMapper.userToUserDto(savedUser));
    }

    @Override
    public Response<ErrorResponse, String> activateAccount(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("User not found"));
        if (user.getIsActive().equals(true)) throw new ConflictException("Your account was activate");
        user.setIsActive(true);
        userRepository.save(user);
        return new Response<>(null, "Your account has activated");
    }


    private String generateActivationLink(String password, Long id) {
        String link = "http://localhost:8080/v1/auth/activation/" + id;
        return "Please click on this link \n" + link + "\n" + "This is your password : " + password +
                "\n" + "Please change your password";
    }

    private String generateRandomPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(3, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(3);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    private void setTokens(HttpServletResponse response, String email, User user) {
        List<String> tokens = jwtProvider.createTokens(email, user.getRole());
        user.setRefreshToken(tokens.get(1));
        userRepository.save(user);
        response.setHeader("access_token", tokens.get(0));
        response.setHeader("refresh_token", tokens.get(1));
    }
}
