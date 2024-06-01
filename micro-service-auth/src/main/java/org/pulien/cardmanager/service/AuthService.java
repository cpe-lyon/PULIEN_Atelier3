package org.pulien.cardmanager.service;

import lombok.AllArgsConstructor;
import org.pulien.cardmanager.authentification.JwtUtil;
import org.pulien.cardmanager.request.CheckTokenRequest;
import org.pulien.cardmanager.request.LoginRequest;
import org.pulien.cardmanager.request.UserDTO;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public String gerenateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public Boolean checkToken(CheckTokenRequest checkTokenRequest) {
        String login = jwtUtil.extractUsername(checkTokenRequest.getToken());
        return jwtUtil.validateToken(checkTokenRequest.getToken(), login);
    }

    public String login(LoginRequest loginRequest) throws LoginException {
        if (userService.isValidPassword(loginRequest.getUsername(), loginRequest.getPassword())){
            return gerenateToken(loginRequest.getUsername());
        } else {
            throw new LoginException("Login or password are wrong!");
        }
    }

    public UserDTO register(UserDTO userDTO) {
        return userService.register(userDTO);
    }

    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    public UserDTO extractUserIdFromToken(String userName) {
        return userService.getUserIdFromUserName(userName);
    }
}
