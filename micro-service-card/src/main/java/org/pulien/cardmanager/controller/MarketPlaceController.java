package org.pulien.cardmanager.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.pulien.cardmanager.entity.CardInstance;
import org.pulien.cardmanager.exception.*;
import org.pulien.cardmanager.service.MarketPlaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/marketPlace")
public class MarketPlaceController {
    private final MarketPlaceService marketPlaceService;

    @GetMapping
    public ResponseEntity<Page<CardInstance>> getMarketplace(@PageableDefault(value = 5) Pageable pageable, @RequestAttribute Long user_id) {
        return ResponseEntity.ok(marketPlaceService.getMarketplace(pageable, user_id));
    }

    @PostMapping("/isCardInstancePurchasableByUser/{cardInstanceId}")
    public ResponseEntity<Boolean> isCardInstancePurchasableByUser(@NonNull @RequestAttribute Long user_id, @NonNull @PathVariable Long cardInstanceId) throws MarketPlaceException {
        return ResponseEntity.ok(marketPlaceService.isCardInstancePurchasableByUserId(user_id, cardInstanceId));
    }

    @PostMapping("/affectCardInstance/{cardInstanceId}")
    public ResponseEntity<CardInstance> affectCardInstance(@NonNull @RequestAttribute Long user_id, @NonNull @PathVariable Long cardInstanceId) throws UpdateCardInstanceException, CardInstanceNotFoundException {
        return ResponseEntity.ok(marketPlaceService.affectCardInstance(cardInstanceId, user_id));
    }


    @PostMapping("/sell/{cardInstanceId}")
    public ResponseEntity<CardInstance> sellACard(@NonNull @RequestAttribute Long user_id, @NonNull @PathVariable Long cardInstanceId) throws AuthorizationException, UpdateCardInstanceException, CardInstanceNotFoundException, CardNotFoundException {
            return ResponseEntity.ok(marketPlaceService.sell(cardInstanceId, user_id));
    }
}