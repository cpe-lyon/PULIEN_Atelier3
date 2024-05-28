package org.pulien.microserviceUser.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pulien.microserviceUser.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements DTO<User> {
    private String firstname;
    private String lastname;
    private String login;
    private String email;
    private String password;
    private int cash;

    @Override
    public User toEntity() {
        return User.builder()
                .lastname(lastname)
                .login(login)
                .email(email)
                .password(password)
                .cash(cash)
                .firstname(firstname)
                .build();
    }
}
