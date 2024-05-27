package org.pulien.cardmanager.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.pulien.cardmanager.entity.Card;
import org.pulien.cardmanager.entity.CardInstance;
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

    public List<Card> getCardsByUserId(Long userId) {
        return cardInstancesRepository.findCardsByUserId(userId);
    }

    public CardInstance saveCardInstance(CardInstance cardInstance) {
        return this.cardInstancesRepository.save(cardInstance);
    }

    @Transactional // Atomicity
    public CardInstance updateCardInstance(Long id, CardInstance newCardInstance) {
        return cardInstancesRepository.findById(id).map(cardInstance -> {
            cardInstance.setUser_id(newCardInstance.getUser_id());
            cardInstance.setCard_id(newCardInstance.getCard_id());
            cardInstance.setIsBuyable(newCardInstance.getIsBuyable());
            return this.cardInstancesRepository.save(cardInstance);
        }).orElseThrow(() -> new RuntimeException("CardInstance not found with id " + id));
    }

    public List<CardInstance> saveCardInstances(List<CardInstance> cardInstances) {
        return this.cardInstancesRepository.saveAll(cardInstances);
    }

    public List<CardInstance> getAllCardInstances() {
        return this.cardInstancesRepository.findAll();
    }

    public Optional<CardInstance> getCardInstanceById(Long id) {
        return this.cardInstancesRepository.findById(id);
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

    public List<CardInstance> createCardInstance(List<Card> cards, Long user_id, boolean isBuyable) throws BadRequestException {
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

    public List<CardInstance> getCardsByUserLogin(String username) {
        return cardInstancesRepository.findCardInstanceByUserLogin(username);
    }

    public Page<CardInstance> getBuyableCardInstances(Pageable pageable, String login) {
        return cardInstancesRepository.findByBuyableIsTrue(pageable, login);
    }
}
