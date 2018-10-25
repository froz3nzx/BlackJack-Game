package cardgame;

import java.util.concurrent.ThreadLocalRandom;

public class Deck {
    private Card[] deck;
    private int cardsUsed;

    //creates a deck of 52 cards
    public Deck(){
        deck = new Card[52];
        cardsUsed = 0;

        //creates the deck
        int index = 0;
        for (Value value : Value.values()){
            for (Suit suit : Suit.values()){
                deck[index] = new Card(value, suit);
                index++;
            }
        }

        shuffle();
    }

    //shuffles the deck
    public void shuffle(){
        for (int i = 0; i < deck.length; i++) {
            int random = ThreadLocalRandom.current().nextInt(0, 52);
            Card tmp = deck[i];
            deck[i] = deck[random];
            deck[random] = tmp;
        }
        cardsUsed = 0;
    }


    public void printDeck(){
        for (Card card:deck){
            System.out.println(card);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Card[] getDeck() {
        return deck;
    }

    public Card dealCard(){
        if(cardsUsed == 52)
            shuffle();
        cardsUsed++;
        return deck[cardsUsed-1];
    }

    public int getCardsLeft(){
        return deck.length - cardsUsed;
    }
}
