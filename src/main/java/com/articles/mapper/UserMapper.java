package com.articles.mapper;

import com.articles.dto.user.UserRequest;
import com.articles.dto.user.UserResponse;
import com.articles.model.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse userToUserDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public static User userRegisterToUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .build();
    }

}
