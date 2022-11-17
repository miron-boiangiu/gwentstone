package game;

import fileio.CardInput;

public class Hero extends Card{
    private int health = 30;
    public Hero(CardInput cardInfo) {
        super(cardInfo);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
