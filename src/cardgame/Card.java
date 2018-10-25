package cardgame;

public class Card {
    private final Value value;
    private final Suit suit;

    //creates a new card with specified suit and value
    public Card(Value value, Suit suit) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }

    public int getIntValue() {
        return value.getValue();
    }

    public String getValueAsString() {
        switch (value) {
            case ACE:
                return "A";
            case TWO:
                return "2";
            case THREE:
                return "3";
            case FOUR:
                return "4";
            case FIVE:
                return "5";
            case SIX:
                return "6";
            case SEVEN:
                return "7";
            case EIGHT:
                return "8";
            case NINE:
                return "9";
            case TEN:
                return "10";
            case JACK:
                return "J";
            case QUEEN:
                return "Q";
            case KING:
                return "K";
            default:
                return "?";
        }

    }

    public String getSuitAsString() {
        switch (suit) {
            case CLUBS:
                return suit.name();
            case HEARTS:
                return suit.name();
            case SPADES:
                return suit.name();
            case DIAMONDS:
                return suit.name();
            default:
                return "?";
        }
    }

    @Override
    public String toString() {
        return getIntValue() + " of " + getSuitAsString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            return ((((Card) obj).value == value) && (((Card) obj).suit == suit));
        }
        return false;
    }
}
