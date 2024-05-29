package org.pulien.cardmanager.service;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pulien.cardmanager.entity.Card;
import org.pulien.cardmanager.entity.CardInstance;
import org.pulien.cardmanager.exception.*;
import org.pulien.cardmanager.repository.card.CardInstancesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardsInstanceServiceTest {

    @Mock
    private CardInstancesRepository cardInstancesRepository;

    @Mock
    private CardsService cardsService;

    @InjectMocks
    private CardsInstanceService cardsInstanceService;

    private CardInstance cardInstance;
    private List<CardInstance> cardInstances;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        cardInstance = new CardInstance();
        cardInstance.setCard_id(1L);
        cardInstance.setUser_id(1L);
        cardInstance.setIsBuyable(true);

        cardInstances = Arrays.asList(cardInstance);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testGetAllCardInstances() {
        when(cardInstancesRepository.findAll()).thenReturn(cardInstances);

        List<CardInstance> result = cardsInstanceService.getAllCardInstances();

        assertEquals(cardInstances, result);
        verify(cardInstancesRepository, times(1)).findAll();
    }

    @Test
    void testGetCardInstanceById_Success() throws CardInstanceNotFoundException {
        when(cardInstancesRepository.findById(1L)).thenReturn(Optional.of(cardInstance));

        CardInstance result = cardsInstanceService.getCardInstanceById(1L);

        assertEquals(cardInstance, result);
        verify(cardInstancesRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCardInstanceById_NotFound() {
        when(cardInstancesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CardInstanceNotFoundException.class, () -> {
            cardsInstanceService.getCardInstanceById(1L);
        });

        verify(cardInstancesRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveCardInstance_Success() throws SavingCardInstanceException {
        when(cardInstancesRepository.save(any(CardInstance.class))).thenReturn(cardInstance);

        CardInstance result = cardsInstanceService.saveCardInstance(cardInstance);

        assertEquals(cardInstance, result);
        verify(cardInstancesRepository, times(1)).save(cardInstance);
    }

    @Test
    void testSaveCardInstance_Failure() {
        when(cardInstancesRepository.save(any(CardInstance.class))).thenReturn(null);

        assertThrows(SavingCardInstanceException.class, () -> {
            cardsInstanceService.saveCardInstance(cardInstance);
        });

        verify(cardInstancesRepository, times(1)).save(cardInstance);
    }

    @Test
    void testSaveCardInstances_Success() throws SavingCardInstanceException {
        when(cardInstancesRepository.saveAll(cardInstances)).thenReturn(cardInstances);

        List<CardInstance> result = cardsInstanceService.saveCardInstances(cardInstances);

        assertEquals(cardInstances, result);
        verify(cardInstancesRepository, times(1)).saveAll(cardInstances);
    }

    @Test
    void testSaveCardInstances_Failure() {
        when(cardInstancesRepository.saveAll(cardInstances)).thenReturn(Arrays.asList());

        assertThrows(SavingCardInstanceException.class, () -> {
            cardsInstanceService.saveCardInstances(cardInstances);
        });

        verify(cardInstancesRepository, times(1)).saveAll(cardInstances);
    }

    @Test
    void testUpdateCardInstance_Success() throws CardInstanceNotFoundException, UpdateCardInstanceException {
        when(cardInstancesRepository.findById(1L)).thenReturn(Optional.of(cardInstance));
        when(cardInstancesRepository.save(any(CardInstance.class))).thenReturn(cardInstance);

        CardInstance updatedInstance = new CardInstance();
        updatedInstance.setCard_id(2L);
        updatedInstance.setUser_id(2L);
        updatedInstance.setIsBuyable(false);

        CardInstance result = cardsInstanceService.updateCardInstance(1L, updatedInstance);

        assertEquals(cardInstance, result);
        verify(cardInstancesRepository, times(1)).findById(1L);
        verify(cardInstancesRepository, times(1)).save(cardInstance);
    }

    @Test
    void testUpdateCardInstance_NotFound() {
        when(cardInstancesRepository.findById(1L)).thenReturn(Optional.empty());

        CardInstance updatedInstance = new CardInstance();
        updatedInstance.setCard_id(2L);
        updatedInstance.setUser_id(2L);
        updatedInstance.setIsBuyable(false);

        assertThrows(CardInstanceNotFoundException.class, () -> {
            cardsInstanceService.updateCardInstance(1L, updatedInstance);
        });

        verify(cardInstancesRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateCardInstance_Failure() throws CardInstanceNotFoundException {
        when(cardInstancesRepository.findById(1L)).thenReturn(Optional.of(cardInstance));
        when(cardInstancesRepository.save(any(CardInstance.class))).thenReturn(null);

        CardInstance updatedInstance = new CardInstance();
        updatedInstance.setCard_id(2L);
        updatedInstance.setUser_id(2L);
        updatedInstance.setIsBuyable(false);

        assertThrows(UpdateCardInstanceException.class, () -> {
            cardsInstanceService.updateCardInstance(1L, updatedInstance);
        });

        verify(cardInstancesRepository, times(1)).findById(1L);
        verify(cardInstancesRepository, times(1)).save(cardInstance);
    }

    @Test
    void testCreateCardInstance_Success() throws BadRequestException {
        when(cardInstancesRepository.save(any(CardInstance.class))).thenReturn(cardInstance);

        CardInstance result = null;
        result = cardsInstanceService.createCardInstance(1L, 1L, true);

        assertEquals(cardInstance, result);
        verify(cardInstancesRepository, times(1)).save(any(CardInstance.class));
    }

    @Test
    void testCreateCardInstance_Failure() {
        when(cardInstancesRepository.save(any(CardInstance.class))).thenReturn(null);

        assertThrows(BadRequestException.class, () -> {
            cardsInstanceService.createCardInstance(1L, 1L, true);
        });

        verify(cardInstancesRepository, times(1)).save(any(CardInstance.class));
    }

    @Test
    void testCreateCardInstances_Success() throws BadRequestException, org.apache.coyote.BadRequestException {
        List<Card> cards = Arrays.asList(new Card());

        when(cardInstancesRepository.saveAll(anyList())).thenReturn(cardInstances);

        List<CardInstance> result = cardsInstanceService.createCardInstances(cards, 1L, true);

        assertEquals(cardInstances, result);
        verify(cardInstancesRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testCreateCardInstances_Failure() {
        List<Card> cards = Arrays.asList(new Card());

        when(cardInstancesRepository.saveAll(anyList())).thenReturn(Arrays.asList());

        assertThrows(BadRequestException.class, () -> {
            cardsInstanceService.createCardInstances(cards, 1L, true);
        });

        verify(cardInstancesRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetBuyableCardInstances() {
        Page<CardInstance> page = new PageImpl<>(cardInstances);
        when(cardInstancesRepository.findByBuyableIsTrue(pageable, 1L)).thenReturn(page);

        Page<CardInstance> result = cardsInstanceService.getBuyableCardInstances(pageable, 1L);

        assertEquals(page, result);
        verify(cardInstancesRepository, times(1)).findByBuyableIsTrue(pageable, 1L);
    }

    @Test
    void testGetPriceById_Success() throws CardInstanceNotFoundException, CardNotFoundException {
        when(cardInstancesRepository.findById(1L)).thenReturn(Optional.of(cardInstance));
        when(cardsService.getPriceById(1L)).thenReturn(100);

        int price = cardsInstanceService.getPriceById(1L);

        assertEquals(100, price);
        verify(cardInstancesRepository, times(1)).findById(1L);
        verify(cardsService, times(1)).getPriceById(1L);
    }

}
