package org.pulien.cardmanager.controller;

import lombok.AllArgsConstructor;
import org.pulien.cardmanager.request.CheckTokenRequest;
import org.pulien.cardmanager.request.TokenGenerationRequest;
import org.pulien.cardmanager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/generateToken")
    public ResponseEntity<String> login(@RequestBody TokenGenerationRequest tokenGenerationRequest) {
        return ResponseEntity.ok(authService.gerenateToken(tokenGenerationRequest.getUsername()));
    }

    @PostMapping("/checktoken")
    public ResponseEntity<Boolean> checkToken(@RequestBody CheckTokenRequest checkTokenRequest) {
        return ResponseEntity.ok(authService.checkToken(checkTokenRequest));
    }
}


