package org.pulien.cardmanager.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.apache.coyote.BadRequestException;
import org.apache.http.HttpException;
import org.pulien.cardmanager.entity.UserDTO;
import org.pulien.cardmanager.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserFeignClientImpl implements UserFeignClient {
    @Autowired
    private final HttpService httpService;

    @Override
    public Long getUserIdByLogin(String userLogin) throws BadRequestException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String response = httpService.sendGetRequest("/user/" + userLogin);
            UserDTO userDTO = objectMapper.readValue(response, UserDTO.class);
            return userDTO.getUserId();
        } catch (JsonProcessingException | HttpException e) {
            throw new BadRequestException("Error with user service interaction");
        }
    }
}
