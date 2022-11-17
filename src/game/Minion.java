package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

abstract public class Minion extends Card{

    protected int frozen = 0;

    protected boolean hasAttacked = false;

    protected boolean isTank = false;

    protected int health = 0;

    protected String row = "Front";

    public void defreezeOneLevel(){
        frozen = Math.max(0, frozen-1);
    }

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

    public void decreaseHealth(int damage){
        health -= damage;
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
