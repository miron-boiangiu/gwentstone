package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

abstract public class Hero extends Card{
    private int health = 30;
    private int mana = 1;

    private boolean hasAttacked = false;
    public Hero(CardInput cardInfo) {
        super(cardInfo);
    }

    protected void computeOutput(ObjectNode outputNode){
        super.computeOutput(outputNode);
        outputNode.put("health", getHealth());
    }

    public abstract String useAbility(int row);

    public boolean canAfford(int mana){
        return this.mana >= mana;
    }

    public void decreaseMana(int mana){
        this.mana -= mana;
        this.mana = Math.max(0, this.mana);
    }

    public int getMana() {
        return mana;
    }

    public void addMana(int mana) {
        mana = Math.min(mana, 10);
        this.mana += mana;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
