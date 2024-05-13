package org.example.card;

public class CardAbility {
    String abilityText;

    public CardAbility(String abilityText) {
        this.abilityText = abilityText;
    }

    public static CardAbility from(Object ability) {
        if (ability == null) return null;
        if (!(ability instanceof String)) return null;
        var abilityString = ability.toString();
        return new CardAbility(abilityString);
    }

    @Override
    public String toString() {
        return "CardAbility{" +
                "abilityText='" + abilityText.substring(0, 30) + "..." + '\'' +
                '}';
    }
}
