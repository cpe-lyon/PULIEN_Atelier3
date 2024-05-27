package org.pulien.cardmanager.repository.card;

import org.pulien.cardmanager.entity.Card;
import org.pulien.cardmanager.entity.CardInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardInstancesRepository extends JpaRepository<CardInstance, Long> {

    @Query("SELECT ci FROM CardInstance ci WHERE ci.user_id = :user_id")
    List<CardInstance> findCardsByUserId(Long user_id);

    @Query("SELECT ci FROM CardInstance ci WHERE ci.isBuyable = true AND ci.user_id != :user_id" )
    Page<CardInstance> findByBuyableIsTrue(Pageable pageable, Long user_id);
}