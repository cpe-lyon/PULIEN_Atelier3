package org.pulien.cardmanager.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.pulien.cardmanager.entity.Card;
import org.pulien.cardmanager.entity.CardInstance;
import org.pulien.cardmanager.entity.ExtractionUsernameRequest;
import org.pulien.cardmanager.entity.UserDTO;
import org.pulien.cardmanager.exception.*;
import org.pulien.cardmanager.feign.AuthFeignClient;
import org.pulien.cardmanager.feign.UserFeignClient;
import org.pulien.cardmanager.repository.card.CardInstancesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardsInstanceService {
    private final CardInstancesRepository cardInstancesRepository;
    private final CardsService cardsService;
    private final UserFeignClient userFeignClient;
    private final AuthFeignClient authFeignClient;

    public List<CardInstance> getAllCardInstances() {
        return this.cardInstancesRepository.findAll();
    }

    public CardInstance getCardInstanceById(Long id) throws CardInstanceNotFoundException {
        Optional<CardInstance> cardInstance = cardInstancesRepository.findById(id);

        if (cardInstance.isEmpty()){
            throw new CardInstanceNotFoundException("The given id doesn't correspond to any cardinstance");
        }
        return cardInstance.orElse(null);
    }

    /**
     * get all card instances belonging to a user extracted from the bearer token.
     * @param token
     * @return
     */
    public List<CardInstance> getCardsByToken(String token) throws BadRequestException {
        // get username from token
        String username;
        ExtractionUsernameRequest extractionUsernameRequest = new ExtractionUsernameRequest();
        extractionUsernameRequest.setToken(token);

        username = authFeignClient.extractUsernameFromJWT(extractionUsernameRequest);

        if (ObjectUtils.isEmpty(username)) {
            return null;
        }

        // get userId from username
        Long userId = getUserIdFromUserName(username);

        // find card instances from userId
        return cardInstancesRepository.findCardsByUserId(userId);
    }

    public List<CardInstance> getCardsByUserId(Long userId) {
        return cardInstancesRepository.findCardsByUserId(userId);
    }

    private Long getUserIdFromUserName(String userName) throws BadRequestException {
        return this.userFeignClient.getUserIdByLogin(userName);
    }

    public CardInstance saveCardInstance(CardInstance cardInstance) throws SavingCardInstanceException {
        CardInstance savedCardInstance = cardInstancesRepository.save(cardInstance);
        if(savedCardInstance == null){
            throw new SavingCardInstanceException("Error while saving card instance.");
        }
        return savedCardInstance;
    }

    public List<CardInstance> saveCardInstances(List<CardInstance> cardInstances) throws SavingCardInstanceException {
        List<CardInstance> savedCardInstances = cardInstancesRepository.saveAll(cardInstances);
        if (savedCardInstances.isEmpty()){
            throw new SavingCardInstanceException("Error while saving card instances.");
        }
        return savedCardInstances;
    }

    public CardInstance updateCardInstance(Long id, CardInstance newCardInstance) throws CardInstanceNotFoundException, UpdateCardInstanceException {
        CardInstance cardInstance = getCardInstanceById(id);
        cardInstance.setCard(newCardInstance.getCard());
        cardInstance.setIsBuyable(newCardInstance.getIsBuyable());
        cardInstance.setUser_id(newCardInstance.getUser_id());

        CardInstance savedInstance = cardInstancesRepository.save(cardInstance);
        if (savedInstance == null) {
            throw new UpdateCardInstanceException("Error while updating cardinstance with id {}".formatted(id));
        }

        return savedInstance;
    }


    public Page<CardInstance> getBuyableCardInstances(Pageable pageable, Long user_id) {
        return cardInstancesRepository.findByBuyableIsTrue(pageable, user_id);
    }
}
