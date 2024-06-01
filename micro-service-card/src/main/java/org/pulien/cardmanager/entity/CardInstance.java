package org.pulien.cardmanager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "card_instances", schema = "pulien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "is_buyable")
    private Boolean isBuyable;
}
