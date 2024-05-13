package org.example.card;

import clojure.lang.Keyword;

import java.util.Map;

public class CardCost {
    Long white;
    Long blue;
    Long black;
    Long red;
    Long green;
    Long colorless;

    private static Keyword kWhite = Keyword.intern("white");
    private static Keyword kBlue = Keyword.intern("blue");
    private static Keyword kBlack = Keyword.intern("black");
    private static Keyword kRed = Keyword.intern("red");
    private static Keyword kGreen = Keyword.intern("green");
    private static Keyword kColorless = Keyword.intern("colorless");

    public CardCost(Long white, Long blue, Long black, Long red, Long green, Long colorless) {
        this.white = white;
        this.blue = blue;
        this.black = black;
        this.red = red;
        this.green = green;
        this.colorless = colorless;
    }

    public static CardCost from(Object cost) {
        if (cost == null) return null;
        if (!(cost instanceof Map)) throw new UnsupportedOperationException("CardCost is not a map");
        var costMap = (Map) cost;
        return new CardCost(
                (Long) costMap.get(kWhite),
                (Long) costMap.get(kBlue),
                (Long) costMap.get(kBlack),
                (Long) costMap.get(kRed),
                (Long) costMap.get(kGreen),
                (Long) costMap.get(kColorless)
        );
    }

    @Override
    public String toString() {
        return "CardCost{" +
                "white=" + white +
                ", blue=" + blue +
                ", black=" + black +
                ", red=" + red +
                ", green=" + green +
                ", colorless=" + colorless +
                '}';
    }
}
