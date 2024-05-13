package org.example.card;

import clojure.lang.Keyword;

public enum CardSubtype {
    GOBLIN
    ;

    private static CardSubtype from(String cardSubtype) {
        return switch (cardSubtype.toLowerCase()) {
            case ":goblin" -> CardSubtype.GOBLIN;
            default -> throw new UnsupportedOperationException("Unknown card subtype: " + cardSubtype);
        };
    }

    public static CardSubtype from(Object cardSubtype) {
        if (cardSubtype == null) return null;
        if (!(cardSubtype instanceof Keyword)) throw new UnsupportedOperationException("Cannot convert CardSubtype from: " + cardSubtype.getClass());
        var keyword = (Keyword) cardSubtype;
        var keywordString = keyword.toString();
        return CardSubtype.from(keywordString);
    }
}
