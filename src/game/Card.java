package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

abstract public class Card {
    protected CardInput cardInfo;

    protected boolean placeable = false;

    public Card(CardInput cardInfo){
        this.cardInfo = cardInfo;
    }

    public ObjectNode addOutputNode(ObjectNode node){
        ObjectNode outputNode = node.putObject("output");
        computeOutput(outputNode);
        return node;
    }
    public ArrayNode addOutputNode(ArrayNode node){
        ObjectNode outputNode = node.addObject();
        computeOutput(outputNode);
        return node;
    }

    public boolean isPlaceable(){
        return placeable;
    }

    protected void computeOutput(ObjectNode outputNode){
        outputNode.put("mana", getCardInfo().getMana());
        outputNode.put("description", getCardInfo().getDescription());
        ArrayNode new_array_node_for_colors = outputNode.withArray("colors");
        for(String color: getCardInfo().getColors()){
            new_array_node_for_colors.add(color);
        }
        outputNode.put("name", getCardInfo().getName());
    }

    public CardInput getCardInfo() {
        return cardInfo;
    }
}
