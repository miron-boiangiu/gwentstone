package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

abstract public class Minion extends Card{

    protected int frozen = 0;

    protected int hasAttacked = 0;

    protected boolean isTank = false;

    protected int health = 0;

    protected void computeOutput(ObjectNode outputNode){
        super.computeOutput(outputNode);
        outputNode.put("health", getHealth());
        outputNode.put("attackDamage", getCardInfo().getAttackDamage());
    }

    public Minion(CardInput cardInfo) {
        super(cardInfo);
        health = cardInfo.getHealth();
    }

    public int getHasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(int hasAttacked) {
        this.hasAttacked = hasAttacked;
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
