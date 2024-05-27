package org.pulien.microserviceUser.controllers;

import lombok.AllArgsConstructor;
import org.pulien.microserviceUser.entity.User;
import org.pulien.microserviceUser.exception.RegistrationException;
import org.pulien.microserviceUser.models.dtos.UserDTO;
import org.pulien.microserviceUser.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/{userLogin}/id")
    public ResponseEntity<Long> getUserIdByLogin(@PathVariable String userLogin) {
        Optional<Long> userId = userService.getUserIdByLogin(userLogin);

        if (userId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return userId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{userLogin}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String userLogin) {
        return ResponseEntity.ok(userService.getByLogin(userLogin));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<User> saveNewUser(@RequestBody UserDTO userDTO) throws RegistrationException {
        return ResponseEntity.ok(userService.register(userDTO));
    }
}
