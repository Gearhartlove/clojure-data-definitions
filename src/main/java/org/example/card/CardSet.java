package org.example.card;

import clojure.lang.Keyword;

public enum CardSet {
    SCOURGE
    ;

    private static CardSet from(Keyword set) {
        return switch (set.toString().toLowerCase()) {
            case ":scourge" -> SCOURGE;
            default -> throw new IllegalArgumentException("Unknown set: " + set);
        };
    }

    public static CardSet from(Object set) {
        if (set == null) return null;
        if (!(set instanceof Keyword)) throw new IllegalArgumentException("Card's set must be a keyword. Got: " + set.getClass());
        var setKeyword = (Keyword) set;
        return from(setKeyword);
    }
}
