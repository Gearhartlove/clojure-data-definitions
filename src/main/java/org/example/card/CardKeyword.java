package org.example.card;

import clojure.lang.Keyword;

import java.util.ArrayList;
import java.util.List;

public enum CardKeyword {
    HASTE
    ;

    private static CardKeyword from(Keyword keyword) {
        return switch (keyword.toString().toLowerCase()) {
            case ":haste" -> CardKeyword.HASTE;
            default -> throw new UnsupportedOperationException("Unknown keyword: " + keyword);
        };
    }

    public static List<CardKeyword> from(Object keywords) {
        if (keywords == null) return null;
        if (!(keywords instanceof Iterable)) throw new UnsupportedOperationException("Keywords must be iterable. Found: " + keywords.getClass());
        var resultList = new ArrayList<CardKeyword>();
        var keywordsIterable = (Iterable) keywords;
        for (var k : keywordsIterable) {
            if (!(k instanceof Keyword)) throw new UnsupportedOperationException("Keywords elements must be keywords. Found:" + k.getClass());
            var keyword = (Keyword) k;
            var cardKeyword = from(keyword);
            resultList.add(cardKeyword);
        }
        return resultList;
    }
}
