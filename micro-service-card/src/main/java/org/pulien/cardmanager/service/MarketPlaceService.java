package org.pulien.cardmanager.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.pulien.cardmanager.entity.CardInstance;
import org.pulien.cardmanager.entity.ExtractionUsernameRequest;
import org.pulien.cardmanager.exception.*;
import org.pulien.cardmanager.feign.AuthFeignClient;
import org.pulien.cardmanager.feign.UserFeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MarketPlaceService{

    private CardsInstanceService cardsInstanceService;
    private final AuthFeignClient authFeignClient;
    private final UserFeignClient userFeignClient;


    public boolean isCardInstancePurchasableByUserId(Long user_id, Long cardInstanceId) throws MarketPlaceException {
        try {
            CardInstance cardInstance = getById(cardInstanceId);
            return !cardInstance.getUser_id().equals(user_id);
        } catch (CardInstanceNotFoundException exception){
            throw new MarketPlaceException("Error while fetching card instance for id {}".formatted(cardInstanceId), exception);
        }
    }

    public CardInstance buy(String token, Long cardInstanceId) throws AuthorizationException, UpdateCardInstanceException, CardInstanceNotFoundException, BadRequestException, MarketPlaceException {

        Long userId = this.authFeignClient.extractUserIdFromJWT(ExtractionUsernameRequest.builder().token(token).build());

        if (!isCardInstancePurchasableByUserId(userId, cardInstanceId)) {
            throw new AuthorizationException("Not authorized to buy this card !");
        }

        return affectCardInstance(cardInstanceId, userId);
    }

    public CardInstance affectCardInstance(Long cardInstanceId, Long user_id) throws UpdateCardInstanceException, CardInstanceNotFoundException, BadRequestException {
        CardInstance cardInstance = getById(cardInstanceId);

        Long sellerId = cardInstance.getUser_id();

        cardInstance.setUser_id(user_id);
        cardInstance.setIsBuyable(false);


        this.userFeignClient.creditUser(cardInstance.getCard().getPrice(), sellerId);
        this.userFeignClient.debitUser(cardInstance.getCard().getPrice(), user_id);

        return cardsInstanceService.updateCardInstance(cardInstanceId, cardInstance);
    }

    public Page<CardInstance> getMarketplace(Pageable pageable, String token) throws BadRequestException {
        Long userId = this.authFeignClient.extractUserIdFromJWT(ExtractionUsernameRequest.builder().token(token).build());

        return this.getMarketplace(pageable, userId);
    }

    public Page<CardInstance> getMarketplace(Pageable pageable, Long user_id) {
        return this.cardsInstanceService.getBuyableCardInstances(pageable, user_id);
    }

    public CardInstance sell(Long cardInstanceId, String token) throws AuthorizationException, UpdateCardInstanceException, CardInstanceNotFoundException, BadRequestException {
        CardInstance cardInstance = cardsInstanceService.getCardInstanceById(cardInstanceId);
        Long userId = this.authFeignClient.extractUserIdFromJWT(ExtractionUsernameRequest.builder().token(token).build());

        if (!Objects.equals(cardInstance.getUser_id(), userId)) {
            throw new AuthorizationException("Not allowed to sell this card!!!");
        }

        if (cardInstance.getIsBuyable()) {
            return cardInstance; // don't need to call db if there is no change
        }

        cardInstance.setIsBuyable(true);

        return cardsInstanceService.updateCardInstance(cardInstanceId, cardInstance);
    }

    private CardInstance getById(Long cardInstanceId) throws CardInstanceNotFoundException {
        return cardsInstanceService.getCardInstanceById(cardInstanceId);
    }
}
