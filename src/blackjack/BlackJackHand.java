package blackjack;

import cardgame.*;


public class BlackJackHand extends Hand {
    private int handValue;
    private int cardsNumber;

    //depending on how much cards are in the hand it calculates the value of the hand following blackjack rules
    public int getHandValue() {
        //resets the counters for correct calculations
        cardsNumber = getCardsCount();
        handValue = 0;

        //cycles through all cards in the hand calculating its value
        for (int i = 0; i < cardsNumber; i++) {
            Card c;
            c = getCard(i);
            int cardValue = c.getIntValue();

            //jack queen and king values are set to 10
            //if hand's value is 11 or more ace value is 1 else 11
            switch (c.getValue()) {
                case ACE:
                    if (handValue >= 11)
                        cardValue = 1;
                    else {
                        cardValue = 11;
                    }
                    break;
                case JACK:
                case QUEEN:
                case KING:
                    cardValue = 10;
                    break;
            }

            //updates the hand value
            handValue += cardValue;
        }
        return handValue;
    }


    public boolean isBlackJackHand() {
        return getHandValue() == 21;
    }
}
