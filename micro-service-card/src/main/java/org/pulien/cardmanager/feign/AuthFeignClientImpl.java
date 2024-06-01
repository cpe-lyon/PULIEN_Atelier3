package org.pulien.cardmanager.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.http.HttpException;
import org.pulien.cardmanager.entity.ExtractionUsernameRequest;
import org.pulien.cardmanager.entity.UserDTO;
import org.pulien.cardmanager.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
@Slf4j
public class AuthFeignClientImpl implements AuthFeignClient {
    @Autowired
    private final HttpService httpService;

    @Override
    public String extractUsernameFromJWT(ExtractionUsernameRequest token) throws BadRequestException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> payload = new HashMap<>();
        payload.put("token", token.getToken());

        try {
            return httpService.sendPostRequest("/auth/extractUsername", objectMapper.writeValueAsString(payload));
        } catch (JsonProcessingException | HttpException e) {
            throw new BadRequestException("Error with user service interaction");
        }
    }

    @Override
    public Long extractUserIdFromJWT(ExtractionUsernameRequest token) throws BadRequestException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> payload = new HashMap<>();
        payload.put("token", token.getToken());

        try {
            return Long.valueOf(httpService.sendPostRequest("/auth/extractUserId", objectMapper.writeValueAsString(payload)));
        } catch (JsonProcessingException | HttpException e) {
            throw new BadRequestException("Error with user service interaction");
        }    }


}
