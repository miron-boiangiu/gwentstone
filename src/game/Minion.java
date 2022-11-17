package game;

import fileio.CardInput;

abstract public class Minion extends Card{

    protected int frozen = 0;

    protected int hasAttacker = 0;

    protected boolean isTank = false;

    protected int health = 0;

    public Minion(CardInput cardInfo) {
        super(cardInfo);
        health = cardInfo.getHealth();
    }

    public int getHasAttacker() {
        return hasAttacker;
    }

    public void setHasAttacker(int hasAttacker) {
        this.hasAttacker = hasAttacker;
    }

    public int getFrozen() {
        return frozen;
    }

    public void setFrozen(int frozen) {
        this.frozen = frozen;
    }

    public boolean isTank() {
        return isTank;
    }

    public void setTank(boolean tank) {
        isTank = tank;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
