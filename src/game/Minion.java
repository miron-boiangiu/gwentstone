package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

abstract public class Minion extends Card{

    protected int frozen = 0;

    protected int damage = 0;

    protected boolean hasAttacked = false;

    protected boolean isTank = false;

    protected int health = 0;

    protected String row = "Front";

    public void defreezeOneLevel(){
        frozen = Math.max(0, frozen-1);
    }

    public String attack(int attacked_x, int attacker_y){
        Table table = MainGame.getInstance().getTable();
        int player_no = MainGame.getInstance().getCurrentTurn();
        int enemy_first_row = 2*player_no - 2;
        Minion attacked = table.getTableRows()[attacked_x].get(attacker_y);
        if(attacked_x != enemy_first_row && attacked_x != enemy_first_row + 1){
            return "Attacked card does not belong to the enemy.";
        }
        else if(getHasAttacked()){
            return "Attacker card has already attacked this turn.";
        }
        else if(isFrozen()){
            return "Attacker card is frozen.";
        } else if ((table.rowHasTank(enemy_first_row) || table.rowHasTank( enemy_first_row + 1))
                && !(attacked.isTank())) {
            return "Attacked card is not of type 'Tank'.";
        }
        setHasAttacked(true);
        attacked.decreaseHealth(getDamage());
        return null;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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
        this.damage = cardInfo.getAttackDamage();
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

    public boolean isFrozen(){
        return(getFrozen() != 0);
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
