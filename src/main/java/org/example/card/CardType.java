package org.example.card;

import clojure.lang.Keyword;

public enum CardType {
    CREATURE,
    LEGENDARY_CREATURE
    ;

    private static CardType from(String cardType) {
        return switch (cardType.toLowerCase()) {
            case ":creature" ->  CardType.CREATURE;
            case ":legendary-creature" -> CardType.LEGENDARY_CREATURE;
            default -> throw new UnsupportedOperationException("Unknown card type: " + cardType);
        };
    }

    public static CardType from(Object cardType) {
        if (cardType == null) return null;
        if (!(cardType instanceof Keyword)) throw new UnsupportedOperationException("Cannot convert CardType from: " + cardType.getClass());
        var keyword = (Keyword) cardType;
        var keywordString = keyword.toString();
        return CardType.from(keywordString);
    }
}
