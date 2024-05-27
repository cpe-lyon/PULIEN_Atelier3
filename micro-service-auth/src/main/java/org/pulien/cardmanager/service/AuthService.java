package org.pulien.cardmanager.service;

import lombok.AllArgsConstructor;
import org.pulien.cardmanager.authentification.JwtUtil;
import org.pulien.cardmanager.request.CheckTokenRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;

    public String gerenateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public Boolean checkToken(CheckTokenRequest checkTokenRequest) {
        String login = jwtUtil.extractUsername(checkTokenRequest.getToken());
        return jwtUtil.validateToken(checkTokenRequest.getToken(), login);
    }
}
