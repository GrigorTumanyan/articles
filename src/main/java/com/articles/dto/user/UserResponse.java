package com.articles.dto.user;

import com.articles.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private Role role;

}
