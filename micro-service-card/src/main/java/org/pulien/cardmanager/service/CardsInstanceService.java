package org.pulien.cardmanager.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.pulien.cardmanager.entity.Card;
import org.pulien.cardmanager.entity.CardInstance;
import org.pulien.cardmanager.exception.*;
import org.pulien.cardmanager.repository.card.CardInstancesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardsInstanceService {
    private final CardInstancesRepository cardInstancesRepository;
    private final CardsService cardsService;

    public List<CardInstance> getAllCardInstances() {
        return this.cardInstancesRepository.findAll();
    }

    public CardInstance getCardInstanceById(Long id) throws CardInstanceNotFoundException {
        Optional<CardInstance> cardInstance = cardInstancesRepository.findById(id);

        if (cardInstance.isEmpty()){
            throw new CardInstanceNotFoundException("The given id doesn't correspond to any cardinstance");
        }
        return cardInstance.isPresent() ? cardInstance.get() : null;
    }

    public List<CardInstance> getCardsByUserId(Long userId) {
        return cardInstancesRepository.findCardsByUserId(userId);
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
        cardInstance.setCard_id(newCardInstance.getCard_id());
        cardInstance.setIsBuyable(newCardInstance.getIsBuyable());
        cardInstance.setUser_id(newCardInstance.getUser_id());

        CardInstance savedInstance = cardInstancesRepository.save(cardInstance);
        if (savedInstance == null){
            throw new UpdateCardInstanceException("Error while updating cardinstance with id {}".formatted(id));
        }

        return savedInstance;
    }

    public CardInstance createCardInstance(Long card_id, Long user_id, boolean isBuyable) throws BadRequestException {
        CardInstance cardInstanceToSave = CardInstance.builder()
                .card_id(card_id)
                .user_id(user_id)
                .isBuyable(isBuyable)
                .build();

        CardInstance savedCardInstance = cardInstancesRepository.save(cardInstanceToSave);
        if (savedCardInstance == null){
            throw new BadRequestException("Error while saving card instance.");
        }

        return savedCardInstance;
    }

    public List<CardInstance> createCardInstances(List<Card> cards, Long user_id, boolean isBuyable) throws BadRequestException {
        List<CardInstance> cardInstances = cards.stream().map(card -> CardInstance.builder()
                .card_id(card.getCardId())
                .isBuyable(isBuyable)
                .user_id(user_id)
                .build()).toList();


        List<CardInstance> savedCardInstance = cardInstancesRepository.saveAll(cardInstances);
        if (savedCardInstance.isEmpty()){
            throw new BadRequestException("Error while saving card instances.");
        }

        return savedCardInstance;
    }


    public Page<CardInstance> getBuyableCardInstances(Pageable pageable, Long user_id) {
        return cardInstancesRepository.findByBuyableIsTrue(pageable, user_id);
    }

    public int getPriceById(Long cardInstanceId) throws CardNotFoundException, CardInstanceNotFoundException {
        CardInstance cardInstance = getCardInstanceById(cardInstanceId);
        return cardsService.getPriceById(cardInstance.getCard_id());
    }
}
