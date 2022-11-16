package game;

import fileio.CardInput;

abstract public class Minion extends Card{

    protected int frozen = 0;

    protected int hasAttacker = 0;

    protected boolean isTank = false;

    public Minion(CardInput cardInfo) {
        super(cardInfo);
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
}
