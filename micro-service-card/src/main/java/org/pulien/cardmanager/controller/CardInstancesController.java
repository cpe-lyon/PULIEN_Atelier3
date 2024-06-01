package org.pulien.cardmanager.controller;

import org.apache.coyote.BadRequestException;
import org.pulien.cardmanager.entity.CardInstance;
import org.pulien.cardmanager.exception.CardInstanceNotFoundException;
import org.pulien.cardmanager.exception.SavingCardInstanceException;
import org.pulien.cardmanager.exception.UpdateCardInstanceException;
import org.pulien.cardmanager.exception.UserNotFoundException;
import org.pulien.cardmanager.service.CardsInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cardsInstances")
public class CardInstancesController {
    @Autowired
    private CardsInstanceService cardInstanceService;

    @GetMapping
    public ResponseEntity<List<CardInstance>> getCurrentUserCards(@RequestHeader("Authorization") String token) throws UserNotFoundException, BadRequestException {
        return ResponseEntity.ok(cardInstanceService.getCardsByToken(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardInstance> getCardInstanceById(@PathVariable Long id) throws CardInstanceNotFoundException {
        return ResponseEntity.ok(cardInstanceService.getCardInstanceById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CardInstance>> getAllCardInstances() {
        return ResponseEntity.ok(cardInstanceService.getAllCardInstances());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardInstance>> getCardInstancesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(new ArrayList<>());
        //        return ResponseEntity.ok(cardInstanceService.getCardsByUserId(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<CardInstance> createCardInstance(@RequestBody CardInstance cardInstance) throws SavingCardInstanceException {
        return ResponseEntity.ok(cardInstanceService.saveCardInstance(cardInstance));
    }

    @PostMapping("/createlist")
    public ResponseEntity<List<CardInstance>> createCardIntances(@RequestBody List<CardInstance> cardInstances) throws SavingCardInstanceException {
        return ResponseEntity.ok(cardInstanceService.saveCardInstances(cardInstances));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CardInstance> updateCardInstance(@PathVariable Long id, @RequestBody CardInstance cardInstance) throws UpdateCardInstanceException, CardInstanceNotFoundException {
        return ResponseEntity.ok(cardInstanceService.updateCardInstance(id, cardInstance));
    }
}
