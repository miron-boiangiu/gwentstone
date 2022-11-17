package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();

    public Deck(ArrayList<CardInput> inputCards){
        for(CardInput inputCard: inputCards){
            cards.add( CardFactory.create_card(inputCard) );
        }
    }

    public ObjectNode addOutputNode(ObjectNode node){
        ArrayNode outputNode = node.withArray("output");
        computeOutput(outputNode);
        return node;
    }

    public ArrayNode addOutputNode(ArrayNode node){
        ArrayNode outputNode = node.addArray();
        computeOutput(outputNode);
        return node;
    }

    private void computeOutput(ArrayNode outputNode){
        for(Card card: getCards()){
            card.addOutputNode(outputNode);
        }
    }

    public Card pullCard(){
        return cards.remove(0);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
