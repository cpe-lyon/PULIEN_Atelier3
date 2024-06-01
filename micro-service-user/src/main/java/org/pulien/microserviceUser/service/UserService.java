package org.pulien.microserviceUser.service;

import lombok.AllArgsConstructor;
import org.pulien.microserviceUser.entity.User;

import org.pulien.microserviceUser.exception.AuthServiceException;
import org.pulien.microserviceUser.exception.RegistrationException;
import org.pulien.microserviceUser.exception.TokenException;
import org.pulien.microserviceUser.models.dtos.IsPassordValidRequest;
import org.pulien.microserviceUser.models.dtos.UserDTO;
import org.pulien.microserviceUser.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final EncryptionService encryptionService;
    private final UserRepository userRepository;
    private final AuthService authService;

    public User getByLogin(String login) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        return userOptional.orElse(null);
    }

    /**
     * get an id from login, just already mapped
     * @param login
     * @return
     */
    public Optional<Long> getUserIdByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);

        return user.map(User::getUserId);
    }

    /**
     * Returns a saved user, still have to generate the cards
     * @param user
     * @return
     * @throws RegistrationException
     */
    public User register(UserDTO user) throws RegistrationException {
        User userToSave = user.toEntity();

        User userInDBWithLogin = getByLogin(userToSave.getLogin());

        if (userInDBWithLogin != null) {
            throw new RegistrationException("The given login is already registred !");
        }

        userToSave.setPassword(encryptionService.encryptPassword(userToSave.getPassword()));

        return userRepository.save(userToSave);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Boolean isPasswordValid(IsPassordValidRequest request) {
        User user = getByLogin(request.getUsername());


        return encryptionService.checkPassword(request.getPassword(), user.getPassword());
    }

    public User getUserByToken(String bearerToken) throws TokenException, AuthServiceException {
        if(bearerToken.startsWith("Bearer ")){
            String token = bearerToken.substring(7);
            String username = authService.extractUserName(token);

            return getByLogin(username);
        }else {
            throw new TokenException("The given token is uncorrect");
        }
    }
}
