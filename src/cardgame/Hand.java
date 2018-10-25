package cardgame;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand; //the cards in the hand

    public Hand(){
        hand = new ArrayList<>();
    }

    //add the specified card to the hand
    public void addCard(Card c){
        if (c != null)
            hand.add(c);
    }

    //removes the specified card from the hand
    public void removeCard(Card c){
        hand.remove(c);
    }

    //removes the card from the specified position (index starts from 0)
    public void removeCard(int handPosition){
        try {
            hand.remove(handPosition);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Position not valid! \nValid position: 0-" + (hand.size()-1));
        }
    }

    //returns the card at the specified position (index starts from 0)
    public Card getCard(int position){
        try {
            return (hand.get(position));
        }catch (IndexOutOfBoundsException e){
            System.out.println("Position not valid! \nValid position: 0-" + (hand.size()-1));
            return null;
        }
    }

    public int getCardsCount(){
        return hand.size();
    }

    //removes all cards from hand
    public void clearHand(){
        hand.clear();
    }

    @Override
    public String toString() {
        return hand.toString();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
