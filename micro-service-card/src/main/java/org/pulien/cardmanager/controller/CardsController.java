package org.pulien.cardmanager.controller;

import lombok.NonNull;
import org.pulien.cardmanager.entity.Card;
import org.pulien.cardmanager.exception.CardFilterNotFoundException;
import org.pulien.cardmanager.exception.CardNotFoundException;
import org.pulien.cardmanager.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardsController {
    @Autowired
    private CardsService cardsService;

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@NonNull @PathVariable Long id) throws CardNotFoundException {
        return ResponseEntity.ok(cardsService.getCardById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Card>> getCards() {
        return ResponseEntity.ok(cardsService.getAllCards());
    }

    @GetMapping("/filter/price/{mini}/{max}")
    public ResponseEntity<List<Card>> getCardFilteredByPrice(@PathVariable int mini, @PathVariable int max) throws CardFilterNotFoundException {
        return ResponseEntity.ok(cardsService.getCardFilteredByPrice(mini, max));
    }

    @GetMapping("/filter/price/lower/{max}")
    public ResponseEntity<List<Card>> getCardsCheaperThan(@PathVariable int max) throws CardFilterNotFoundException {
        return ResponseEntity.ok(cardsService.getCardsCheaperThan(max));
    }

    @GetMapping("/filter/price/higher/{mini}")
    public ResponseEntity<List<Card>> getCardsMoreExpensiveThan(@PathVariable int mini) throws CardFilterNotFoundException {
        return ResponseEntity.ok(cardsService.getCardsMoreExpensiveThan(mini));
    }

    @GetMapping("/filter/name/{name}")
    public ResponseEntity<List<Card>> getCardFromName(@PathVariable String name) throws CardFilterNotFoundException {
        return ResponseEntity.ok(cardsService.getCardFromName(name));
    }
}
