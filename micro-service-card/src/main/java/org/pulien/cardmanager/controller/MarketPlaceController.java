package org.pulien.cardmanager.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
@RequestMapping("/marketPlace")
public class MarketPlaceController {
    private final MarketPlaceService marketPlaceService;

    @GetMapping
    public ResponseEntity<Page<CardInstance>> getMarketplace(@PageableDefault(value = 5) Pageable pageable, @RequestHeader("Authorization") String token) throws BadRequestException {
        return ResponseEntity.ok(marketPlaceService.getMarketplace(pageable, token));
    }

    @PostMapping("/buy/{cardInstanceId}")
    public ResponseEntity<CardInstance> buy(@NonNull @RequestHeader("Authorization") String token, @NonNull @PathVariable Long cardInstanceId) throws AuthorizationException, UpdateCardInstanceException, CardInstanceNotFoundException, BadRequestException, MarketPlaceException {
        return ResponseEntity.ok(marketPlaceService.buy(token, cardInstanceId));
    }

    @PostMapping("/sell/{cardInstanceId}")
    public ResponseEntity<CardInstance> sellACard(@NonNull @RequestHeader("Authorization") String token, @NonNull @PathVariable Long cardInstanceId) throws AuthorizationException, UpdateCardInstanceException, CardInstanceNotFoundException, CardNotFoundException, BadRequestException {
            return ResponseEntity.ok(marketPlaceService.sell(cardInstanceId, token));
    }
}