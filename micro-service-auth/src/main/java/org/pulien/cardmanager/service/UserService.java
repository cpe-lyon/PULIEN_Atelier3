package org.pulien.cardmanager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.pulien.cardmanager.request.UserDTO;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final HttpService httpService;

    public boolean isValidPassword(String username, String password) throws LoginException {
        Map<String,String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("feign request to auth ms");
            return httpService.sendPostRequest("/user/isPasswordValid", objectMapper.writeValueAsString(payload)).equals("true");
        } catch (JsonProcessingException | HttpException e) {
            throw new LoginException("Error with user service interaction");
        }
    }

    public UserDTO getUserIdFromUserName(String userName) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            log.info("Feign request to get userId from login");
            return objectMapper.readValue(httpService.sendGetRequest("/user/" + userName), UserDTO.class);
        } catch (HttpException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO register(UserDTO userDTO) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            log.info("Feign request to register user");
            return objectMapper.readValue(httpService.sendPostRequest("/save", objectMapper.writeValueAsString(userDTO)), UserDTO.class);
        } catch (HttpException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
