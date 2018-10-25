package blackjack;

import cardgame.Card;
import cardgame.Deck;
import utils.TextUtils;

//a simple blackjack game
public class BlackJackMain {
    private Player player;
    private BlackJackHand dealerHand;
    private BlackJackHand playerHand;
    private Deck deck = null;
    private GameResult result = GameResult.PLAYING;
    private static boolean isFirstTurn;
    private static double betAmount;

    //manages the core execution flow of the game
    public static void main(String[] args) {
        BlackJackMain currentGame = new BlackJackMain();

        currentGame.player = new Player("Player");

        boolean playing = true;

        currentGame.showRules();
        while (playing) {
            currentGame.askForBet(currentGame.player);
            currentGame.newGame();
            currentGame.play();
            currentGame.checkResults();
            currentGame.finalizeGame();

            playing = currentGame.continuePlaying();
        }
    }

    //asks to continue playing or not
    private boolean continuePlaying() {
        TextUtils.printMessage("Do you wish to continue playing?");
        TextUtils.printMessage("[y] / [n]");
        String choice = TextUtils.readString().toLowerCase();

        //if player has no cash left the game stops
        if (player.getCashAmount() <= 0) {
            TextUtils.printMessage("Sorry you have no cash left! Thanks for playing.");
            return false;
        }
        switch (choice) {
            case "y":
                return true;
            case "n":
            default:
                return false;
        }
    }

    //asks for a valid bet amount
    private void askForBet(Player player) {
        boolean hasMoney = false;
        do {
            TextUtils.printMessage(player.toString());
            TextUtils.printMessage("Enter a bet: ");
            betAmount = TextUtils.readDouble();
            hasMoney = player.bet(betAmount);

            if (!hasMoney)
                TextUtils.printErrMessage("You don't have enough money to bet, please place a lower bet.");

        } while (!hasMoney);
    }

    private void showRules() {
        TextUtils.printMessage("Welcome to BlackJack.");
        TextUtils.printMessage("These are the basic rules: \n" +
                "1) Player wins if his hand's value is greater than the dealer's one, without going " +
                "over 21.\n" +
                "2) The best possible hand is called \"BlackJack\", its value is 21 and consists of an " +
                "ace and any 10-value card.\n" +
                "3) If both dealer and player hands have the same value it's called \"Push\" and you get " +
                "your bet back.\n" +
                "4) At the first turn you can \"Double Down\"n, which enables you to double your bet and" +
                "draw just one card before ending your turn.\n" +
                "5) BlackJack pays 3:2, normal wins 1:1.\n" +
                "6) If either the player or dealer exceed 21 the hand automatically loses.\n" +
                "**The game ends when you quit the application or you loose all cash.**\n"
        );
    }

    //initializing a new game (resets the turns, deals 2 cards, checks for any winner)
    private void newGame() {

        //if it's the first game creates the deck and the hands
        if (deck == null) {
            deck = new Deck();
            deck.shuffle();

            playerHand = new BlackJackHand();
            dealerHand = new BlackJackHand();
        }
        //if there are few cards left it shuffles the deck (aka new deck)
        if (deck.getCardsLeft() <= 6)
            deck.shuffle();

        //deals 2 cards for both player and dealer
        Card card;
        for (int i = 0; i < 2; i++) {
            card = deck.dealCard();
            TextUtils.printMessage("Player hits " + card.getValueAsString() + " of " + card.getSuitAsString());
            playerHand.addCard(card);

            //dealer only shows the first card
            card = deck.dealCard();
            if (dealerHand.getCardsCount() == 0)
                TextUtils.printMessage("Dealer hits " + card.getValueAsString() + " of " + card.getSuitAsString());
            dealerHand.addCard(card);
        }

        showHandValues();

        //checks if there's a blackjack hand
        if (playerHand.isBlackJackHand() && dealerHand.isBlackJackHand())
            result = GameResult.TIE;
        if (playerHand.isBlackJackHand())
            result = GameResult.PLAYER_BLACKJACK;
        if (dealerHand.isBlackJackHand())
            result = GameResult.DEALER_WINS;

        //if there are no winners the game continues normally and the first turn (player's turn) begins
        result = GameResult.PLAYING;
        isFirstTurn = true;
    }

    //user plays the game making choices
    private void play() {

        if (result != GameResult.PLAYING)
            return;

        //shows the choice menu till the player busts/surrenders/stands
        while (playerHand.getHandValue() <= 21) {
            TextUtils.printMessage(player.toString());
            showChoiceMenu();
            int choice = TextUtils.readInt();

            switch (choice) {
                case 1: //player hits
                    playerHits();
                    break;
                case 2: //
                    dealerTurn();
                    return;
                case 3: //surrender
                    result = GameResult.SURRENDER;
                    return;
                case 4: //users doubles the bet and hits one more card
                    doubleDown();
                    if (result == GameResult.PLAYING)
                        dealerTurn();
                    return;
                default:
                    TextUtils.printErrMessage("Not a valid choice.");
            }
        }
    }

    //shows a menu with available choices
    private void showChoiceMenu() {
        TextUtils.printMessage("*******Enter an input*******");

        TextUtils.printMessage("[1] Hit");
        TextUtils.printMessage("[2] Stand");
        if (isFirstTurn) {
            TextUtils.printMessage("[3] Surrender");
            if (player.getCashAmount() >= betAmount)
                TextUtils.printMessage("[4] Double Down");
        }
    }

    //the player doubles his bet and hits only a card
    private void doubleDown() {
        if (player.bet(betAmount)) {
            betAmount *= 2;
            playerHits();
        }

        if (playerHand.getHandValue() >= 21)
            result = GameResult.DEALER_WINS;
    }

    //player hits a card
    private void playerHits() {
        isFirstTurn = false; //first turn has concluded

        Card card = deck.dealCard();
        TextUtils.printMessage("Player hits: " + card.getValueAsString());
        playerHand.addCard(card);

        showHandValues();
    }

    //the dealer stops dealing cards on 17
    private void dealerTurn() {
        showFullHandValues();
        while (dealerHand.getHandValue() <= 16) {
            Card card = deck.dealCard();
            TextUtils.printMessage("Dealer hits " + card.getValueAsString());
            dealerHand.addCard(card);
        }
        if (dealerHand.getHandValue() > 16 && dealerHand.getHandValue() <= 21)
            TextUtils.printMessage("Dealer stands.");
        else
            TextUtils.printMessage("Dealer busts.");
    }

    //checks all possible results and assigns the result to result
    private void checkResults() {
        if (result == GameResult.SURRENDER) return;

        //dealer wins if his hand's value is higher than player's
        if (dealerHand.getHandValue() > playerHand.getHandValue() && dealerHand.getHandValue() <= 21)
            result = GameResult.DEALER_WINS;

        //dealer automatically wins if player's hand exceeds 21
        if (playerHand.getHandValue() > 21) result = GameResult.DEALER_WINS;

        //dealer loses if his hand's value is lower than player's one and the latter is lower than 21
        if (dealerHand.getHandValue() < playerHand.getHandValue()
                && playerHand.getHandValue() <= 21) result = GameResult.PLAYER_WINS;

        //dealer loses if it busts
        if (dealerHand.getHandValue() > 21 && playerHand.getHandValue() < 21) result = GameResult.PLAYER_WINS;

        //tie if both hands have the same value
        if (dealerHand.getHandValue() == playerHand.getHandValue())
            result = GameResult.TIE;
    }

    private void finalizeGame() {
        showFullHandValues();
        switch (result) {
            case TIE:
                TextUtils.printMessage("It's a tie. You gain your betAmount back");
                player.gainCash(betAmount);
                break;
            case DEALER_WINS:
                TextUtils.printMessage("Dealer wins! Thanks for playing");
                break;
            case PLAYER_WINS:
                TextUtils.printMessage("You win " + betAmount + "$");
                player.gainCash(betAmount * 2);
                break;
            case PLAYER_BLACKJACK:
                TextUtils.printMessage("BlackJack! You win " + betAmount * 1.25 + "$s");
                player.gainCash((betAmount * 2) * 1.25);
                break;
            case SURRENDER:
                TextUtils.printMessage("Try your luck next time. You get back " + betAmount / 2 + "$");
                player.gainCash(betAmount / 2);
                break;
            case PLAYING:
                TextUtils.printMessage("Game still in progress **fix me**");
        }

        dealerHand.clearHand();
        playerHand.clearHand();
        isFirstTurn = false;
    }

    //this method shows only the first card of dealer's hand
    private void showHandValues() {
        TextUtils.printMessage("=============================");
        TextUtils.printMessage("Player's hand value: " + playerHand.getHandValue());
        for (int i = 0; i < playerHand.getCardsCount(); i++)
            System.out.print("[" + playerHand.getCard(i).getValueAsString() + "] ");

        //shows only the first dealer's card
        TextUtils.printMessage("\nDealer's hand value : " + dealerHand.getCard(0).getValueAsString() + " + [?]");
        TextUtils.printMessage("=============================");

    }

    //show both full hands' value
    private void showFullHandValues() {
        TextUtils.printMessage("=============================");
        TextUtils.printMessage("Player's hand value: " + playerHand.getHandValue());
        for (int i = 0; i < playerHand.getCardsCount(); i++)
            System.out.print("[" + playerHand.getCard(i).getValueAsString() + "] ");

        TextUtils.printMessage("\nDealer's hand value: " + dealerHand.getHandValue());
        for (int i = 0; i < dealerHand.getCardsCount(); i++)
            System.out.print("[" + dealerHand.getCard(i).getValueAsString() + "] ");
        TextUtils.printMessage("\n=============================");
    }
}
