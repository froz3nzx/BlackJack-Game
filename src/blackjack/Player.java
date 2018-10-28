package blackjack;


public class Player {
    private String name;
    private double cash;


    //each player starts with 100$
    public Player(String name) {
        this.name = name;
        cash = 100;
    }

    //returns true if betAmount successfully
    public boolean bet(double amount) {
        if (amount > cash) {
            return false;
        } else cash -= amount;
        return true;
    }

    public double getCashAmount() {
        return cash;
    }

    public void gainCash(double amount) {
        this.cash += amount;
    }

    @Override
    public String toString() {
        return name + " | Cash: " + cash + "$";
    }
}
