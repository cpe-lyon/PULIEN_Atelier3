package org.pulien.cardmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.pulien.cardmanager.request.CheckTokenRequest;
import org.pulien.cardmanager.request.ExtractionUsernameRequest;
import org.pulien.cardmanager.request.LoginRequest;
import org.pulien.cardmanager.request.UserDTO;
import org.pulien.cardmanager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws LoginException {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @PostMapping("/checktoken")
    public ResponseEntity<Boolean> checkToken(@RequestBody CheckTokenRequest checkTokenRequest) {
        return ResponseEntity.ok(authService.checkToken(checkTokenRequest));
    }

    @PostMapping("/extractUsername")
    public ResponseEntity<String> checkToken(@RequestBody ExtractionUsernameRequest extractionUsernameRequest) {
        return ResponseEntity.ok(authService.extractUsername(extractionUsernameRequest.getToken()));
    }

    @PostMapping("/extractUserId")
    public ResponseEntity<Long> extractUserIdFromToken(@RequestBody ExtractionUsernameRequest extractionUsernameRequest) throws BadRequestException {
        String username = authService.extractUsername(extractionUsernameRequest.getToken());

        UserDTO userDTO = authService.extractUserIdFromToken(username);

        if (userDTO == null) {
            throw new BadRequestException("User not found");
        }

        return ResponseEntity.ok(userDTO.getUserId());
    }
}


