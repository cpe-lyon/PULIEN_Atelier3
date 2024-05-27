package org.pulien.cardmanager.service;

import lombok.AllArgsConstructor;
import org.pulien.cardmanager.entity.Card;
import org.pulien.cardmanager.exception.CardFilterNotFoundException;
import org.pulien.cardmanager.exception.CardNotFoundException;
import org.pulien.cardmanager.exception.SavingCardException;
import org.pulien.cardmanager.repository.card.CardsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CardsService {
    private final Random random = new Random();
    private final CardsRepository cardsRepository;


    public List<Card> getAllCards() {
        return cardsRepository.findAll();
    }

    public void addCard(Card card) throws SavingCardException {
        Card savedCard = cardsRepository.save(card);
        if(savedCard == null){
            throw new SavingCardException("Error while saving card.");
        }
    }

    public int getPriceById(Long cardId) throws CardNotFoundException {
        Card card = getCardById(cardId);

        if(card == null){
            throw new CardNotFoundException("The given id doesn't correspond to any card");
        }

        return card.getPrice();
    }

    public Card getCardById(Long id) throws CardNotFoundException {
        Optional<Card> card = cardsRepository.findById(id);
        if(card.isEmpty()){
            throw new CardNotFoundException("The given id doesn't correspond to any card.");
        }
        return card.get();
    }

    public List<Card> getCardFilteredByPrice(int mini, int max) throws CardFilterNotFoundException {
        Optional<List<Card>> filteredCards = cardsRepository.findAllByPriceIsBetween(mini, max);
        if (filteredCards.isEmpty()){
            throw new CardFilterNotFoundException("Filter by price between {} and {} doesn't correspond to any cards.".formatted(mini,max));
        }

        return filteredCards.get();
    }

    public List<Card> getCardsCheaperThan(int max) throws CardFilterNotFoundException {
        Optional<List<Card>> filteredCards = cardsRepository.findAllByPriceIsLessThanEqual(max);
        if (filteredCards.isEmpty()){
            throw new CardFilterNotFoundException("Filter by price cheaper than {} doesn't correspond to any cards.".formatted(max));
        }
        return filteredCards.get();
    }

    public List<Card> getCardsMoreExpensiveThan(int mini) throws CardFilterNotFoundException {
        Optional<List<Card>> filteredCards = cardsRepository.findAllByPriceIsGreaterThanEqual(mini);
        if (filteredCards.isEmpty()){
            throw new CardFilterNotFoundException("Filter by price expensive than {} doesn't correspond to any cards.".formatted(mini));
        }
        return filteredCards.get();
    }


    public List<Card> getCardFromName(String name) throws CardFilterNotFoundException {
        Optional<List<Card>> filteredCards = cardsRepository.findAllByName(name);
        if (filteredCards.isEmpty()){
            throw new CardFilterNotFoundException("Filter by name with '{}' input doesn't correspond to any cards.".formatted(name));
        }
        return filteredCards.get();
    }

    public List<Card> getAllByRatingRank(int min, int max) throws CardFilterNotFoundException {
        Optional<List<Card>> filteredCards = cardsRepository.findAllByRatingBetween(min, max);
        if (filteredCards.isEmpty()){
            throw new CardFilterNotFoundException("Filter by rate between {} and {} doesn't correspond to any cards.".formatted(min, max));
        }
        return filteredCards.get();
    }

    public Card getRandomCard() throws CardFilterNotFoundException {
        List<List<Card>> allTiers = new ArrayList<>();

        List<Card> allCardsTier1;
        List<Card> allCardsTier2;
        List<Card> allCardsTier3;

        try {
            allCardsTier1 = getAllByRatingRank(0, 85);
        }catch (CardFilterNotFoundException exception){
            System.out.println("There is no tier 1");
            allCardsTier1 = new ArrayList<>();
        }

        try {
            allCardsTier2 = getAllByRatingRank(86, 90);
        }catch (CardFilterNotFoundException exception){
            System.out.println("There is no tier 2");
            allCardsTier2 = new ArrayList<>();
        }

        try {
            allCardsTier3 = getAllByRatingRank(91, 99);
        }catch (CardFilterNotFoundException exception){
            System.out.println("There is no tier 1");
            allCardsTier3 = new ArrayList<>();
        }


        allTiers.add(allCardsTier1);
        allTiers.add(allCardsTier2);
        allTiers.add(allCardsTier3);

        int tierDrew = random.nextInt(10);
        List<Card> tier;

        if (tierDrew < 6) {
            tier = allTiers.get(0);
        } else if (tierDrew < 9 && allTiers.size() > 1) {
            tier = allTiers.get(1);
        } else if (allTiers.size() > 2) {
            tier = allTiers.get(2);
        } else {
            tier = allTiers.get(0);
        }

        if (tier.isEmpty()) {
            throw new CardFilterNotFoundException("There is no card.");
        }

        return tier.get(random.nextInt(tier.size()));
    }


}
