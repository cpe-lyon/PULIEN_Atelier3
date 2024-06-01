package org.pulien.microserviceUser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.http.HttpException;
import org.pulien.microserviceUser.exception.AuthServiceException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private final HttpService httpService;
    public String extractUserName(String token) throws AuthServiceException {
        Map<String,String> payload = new HashMap<>();
        payload.put("token", token);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return httpService.sendPostRequest("/auth/extractUsername",objectMapper.writeValueAsString(payload));
        } catch (JsonProcessingException | HttpException e) {
            throw new AuthServiceException("Error with user service interaction");
        }

    }
}
