package org.pulien.cardmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import org.pulien.cardmanager.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {
    private final UserHttpService httpService;

    public boolean isValidPassword(String username, String password) throws LoginException {
        Map<String,String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return httpService.sendPostRequest("/users/isPasswordValid",objectMapper.writeValueAsString(payload)).equals("true");
        } catch (JsonProcessingException e) {
            throw new LoginException("Error with user service interaction");
        }
    }
}
