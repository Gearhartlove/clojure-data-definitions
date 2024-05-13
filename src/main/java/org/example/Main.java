package org.example;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Keyword;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.example.card.*;

import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        // setup
        var mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.registerModule(new Jdk8Module());
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("bazaar.core"));

        // grab functions
        IFn fCards = Clojure.var("bazaar.core", "cards");
        IFn fCardNames = Clojure.var("bazaar.core", "card-names");

        // call functions + process data
        Object cardNames = fCardNames.invoke();
        if (cardNames instanceof Iterable) {
            var cardNameIterator = (Iterable) cardNames;

            for (var key : cardNameIterator) {
                if (key instanceof String) {
                    var name = (String) key;
                    var cardWithName = fCards.invoke(name);

                    if (cardWithName == null) throw new RuntimeException("No card with name: " + name);
                    if (!(cardWithName instanceof Map)) throw new RuntimeException("Card: " + name + " must be a map");

                    var card = (Map) cardWithName;

                    var power = Optional.of((Long) (card.get(Keyword.intern("power"))));
                    var toughness = Optional.of((Long) (card.get(Keyword.intern("toughness"))));
                    var cardType = Optional.ofNullable(CardType.from(card.get(Keyword.intern("type"))));
                    var subtype = Optional.ofNullable(CardSubtype.from(card.get(Keyword.intern("subtype"))));
                    var cost = Optional.ofNullable(CardCost.from(card.get(Keyword.intern("cost"))));
                    var ability = Optional.ofNullable(CardAbility.from(card.get(Keyword.intern("ability"))));
                    var keywords = Optional.ofNullable(CardKeyword.from(card.get(Keyword.intern("keywords"))));
                    var set = Optional.ofNullable(CardSet.from(card.get(Keyword.intern("set"))));

                    var constructedCard = new CardModel(
                            name,
                            power,
                            toughness,
                            cardType,
                            subtype,
                            cost,
                            ability,
                            keywords,
                            set
                    );

                    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(constructedCard));
                }
            }
        }
    }
}