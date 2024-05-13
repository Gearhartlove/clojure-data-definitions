# Clojure Data Definitions

Object schemas can bridge the gap between programming and and custom configuration. Functional languages like Scheme or Clojure can be used to generate C header files ro byte code, respectively. When combine these two ideas, you unlock a great deal of cross-team flexibility. Artists and animators now have the confidence to *change code* and programmers have better tooling.

This small toy projects writes the meta of Magic the Gathering cards in Clojure and then converts these objects to java classes to work with. 

In theory you would be able to hot reload these classes and swap the byte code in and out at run time, but I haven't figured that out yet. 

Below is the process I described below : 

# Clojure Definitions
```clojure
(ns bazaar.core)

(def cards {"Goblin King"       {:power     2
                                 :toughness 7
                                 :type      :creature
                                 :subtype   :goblin
                                 :ability   "Other goblin creatures get +1/+1 and have mountainwalk"
                                 :cost      {:white     0
                                             :blue      0
                                             :black     7
                                             :red       2
                                             :green     0
                                             :colorless 1}}
            "Karona, False God" {:power     5
                                 :toughness 5
                                 :type      :legendary-creature
                                 :keywords  [:haste]
                                 :ability   "At the beginning of each player's upkeep, that player untaps Karona, False God and gains control of it.\nWhenever Karona attacks, creatures of the type of your choice get +3/+3 until end of turn"
                                 :cost      {:white     1
                                             :blue      1
                                             :black     1
                                             :red       1
                                             :green     1
                                             :colorless 1}
                                 :set       :scourge}})

(defn card-names [] (keys cards))
```

# Clojure -> Java
```java
// imports ...

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
```

# Printing Java Objects
```json
{
  "name" : "Goblin King",
  "power" : 2,
  "toughness" : 7,
  "cardType" : "CREATURE",
  "cardSubType" : "GOBLIN",
  "cost" : {
    "white" : 0,
    "blue" : 0,
    "black" : 7,
    "red" : 2,
    "green" : 0,
    "colorless" : 1
  },
  "ability" : {
    "abilityText" : "Other goblin creatures get +1/+1 and have mountainwalk"
  },
  "keywords" : null,
  "set" : null
}
{
  "name" : "Karona, False God",
  "power" : 5,
  "toughness" : 5,
  "cardType" : "LEGENDARY_CREATURE",
  "cardSubType" : null,
  "cost" : {
    "white" : 1,
    "blue" : 1,
    "black" : 1,
    "red" : 1,
    "green" : 1,
    "colorless" : 1
  },
  "ability" : {
    "abilityText" : "At the beginning of each player's upkeep, that player untaps Karona, False God and gains control of it.\nWhenever Karona attacks, creatures of the type of your choice get +3/+3 until end of turn"
  },
  "keywords" : [ "HASTE" ],
  "set" : "SCOURGE"
}
```
