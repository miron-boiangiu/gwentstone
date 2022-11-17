package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class Hero extends Card{
    private int health = 30;
    public Hero(CardInput cardInfo) {
        super(cardInfo);
    }

    protected void computeOutput(ObjectNode outputNode){
        super.computeOutput(outputNode);
        outputNode.put("health", getHealth());
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
