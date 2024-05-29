package org.pulien.cardmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {
    private final HttpService httpService;

    public boolean isValidPassword(String username, String password) throws LoginException {
        Map<String,String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return httpService.sendPostRequest("/user/isPasswordValid",objectMapper.writeValueAsString(payload)).equals("true");
        } catch (JsonProcessingException | HttpException e) {
            throw new LoginException("Error with user service interaction");
        }
    }
}
