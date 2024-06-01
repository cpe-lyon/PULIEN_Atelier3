package org.pulien.microserviceUser.controllers;

import lombok.AllArgsConstructor;
import org.pulien.microserviceUser.entity.User;
import org.pulien.microserviceUser.exception.AuthServiceException;
import org.pulien.microserviceUser.exception.RegistrationException;
import org.pulien.microserviceUser.exception.TokenException;
import org.pulien.microserviceUser.models.dtos.IsPassordValidRequest;
import org.pulien.microserviceUser.models.dtos.UserDTO;
import org.pulien.microserviceUser.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/current")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String bearerToken) throws TokenException, AuthServiceException {
        return ResponseEntity.ok(userService.getUserByToken(bearerToken));
    }


    @GetMapping(value = "/{userLogin}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String userLogin) {
        return ResponseEntity.ok(userService.getByLogin(userLogin));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<User> saveNewUser(@RequestBody UserDTO userDTO) throws RegistrationException {
        return ResponseEntity.ok(userService.register(userDTO));
    }

    @PostMapping(value = "/isPasswordValid", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Boolean> isPasswordValid(@RequestBody IsPassordValidRequest request) {
        return ResponseEntity.ok(userService.isPasswordValid(request));
    }
}
