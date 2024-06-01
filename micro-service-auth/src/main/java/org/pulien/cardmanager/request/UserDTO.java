package org.pulien.cardmanager.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String firstname;
    private String lastname;
    private String login;
    private String email;
    private String password;
    private int cash;
}