package org.example.card;

import java.util.List;
import java.util.Optional;

public class CardModel {
    String name;
    Optional<Long> power;
    Optional<Long> toughness;
    Optional<CardType> cardType;
    Optional<CardSubtype> cardSubType;
    Optional<CardCost> cost;
    Optional<CardAbility> ability;
    Optional<List<CardKeyword>> keywords;
    Optional<CardSet> set;

    public CardModel(
            String name,
            Optional<Long> power,
            Optional<Long> toughness,
            Optional<CardType> cardType,
            Optional<CardSubtype> cardSubType,
            Optional<CardCost> cost,
            Optional<CardAbility> ability,
            Optional<List<CardKeyword>> keywords,
            Optional<CardSet> set) {
        this.name = name;
        this.power = power;
        this.toughness = toughness;
        this.cardType = cardType;
        this.cardSubType = cardSubType;
        this.cost = cost;
        this.ability = ability;
        this.keywords = keywords;
        this.set = set;
    }

    @Override
    public String toString() {
        return "CardModel{" +
                "name='" + name + '\'' +
                ", power=" + power +
                ", toughness=" + toughness +
                ", cardType=" + cardType +
                ", cardSubType=" + cardSubType +
                ", cost=" + cost +
                ", ability=" + ability +
                ", keywords=" + keywords +
                ", set=" + set +
                '}';
    }
}



