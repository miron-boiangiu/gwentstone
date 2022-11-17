package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

abstract public class Minion extends Card{

    protected boolean frozen = false;

    protected boolean hasAttacked = false;

    protected boolean isTank = false;

    protected int health = 0;

    protected String row = "Front";

    protected void computeOutput(ObjectNode outputNode){
        super.computeOutput(outputNode);
        outputNode.put("health", getHealth());
        outputNode.put("attackDamage", getCardInfo().getAttackDamage());
    }

    public Minion(CardInput cardInfo) {
        super(cardInfo);
        health = cardInfo.getHealth();
        this.placeable = true;
    }

    public String getRow() {
        return row;
    }

    public boolean getHasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
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
