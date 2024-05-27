package org.pulien.cardmanager.service;

import lombok.AllArgsConstructor;
import org.pulien.cardmanager.entity.CardInstance;
import org.pulien.cardmanager.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MarketPlaceService{

    private CardsInstanceService cardsInstanceService;


    public boolean isCardInstancePurchasableByUserId(Long user_id, Long cardInstanceId) throws MarketPlaceException {
        try {
            CardInstance cardInstance = getById(cardInstanceId);
            if (!cardInstance.getUser_id().equals(user_id)) {
                return true;
            }else {
                return false;
            }
        }catch (CardInstanceNotFoundException exception){
            throw new MarketPlaceException("Error while fetching card instance for id {}".formatted(cardInstanceId), exception);
        }
    }

    public CardInstance affectCardInstance(Long cardInstanceId, Long user_id) throws UpdateCardInstanceException, CardInstanceNotFoundException {
        CardInstance cardInstance = getById(cardInstanceId);
        cardInstance.setUser_id(user_id);

        CardInstance savedCardInstance = cardsInstanceService.updateCardInstance(cardInstanceId, cardInstance);

        return savedCardInstance;
    }


    public Page<CardInstance> getMarketplace(Pageable pageable, Long user_id) {
        return this.cardsInstanceService.getBuyableCardInstances(pageable, user_id);
    }

    public CardInstance sell(Long cardInstanceId, Long user_id) throws CardNotFoundException, AuthorizationException, UpdateCardInstanceException, CardInstanceNotFoundException {
        CardInstance cardInstance = cardsInstanceService.getCardInstanceById(cardInstanceId);

        if (!Objects.equals(cardInstance.getUser_id(), user_id)) {
            throw new AuthorizationException("Not allowed to sell this card!!!");
        }

        cardInstance.setIsBuyable(true);

        return cardsInstanceService.updateCardInstance(cardInstanceId, cardInstance);
    }

    private CardInstance getById(Long cardInstanceId) throws CardInstanceNotFoundException {
        return cardsInstanceService.getCardInstanceById(cardInstanceId);
    }
}
