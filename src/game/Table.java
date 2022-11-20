package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Table {

    protected ArrayList<Minion>[] tableRows = new ArrayList[4];

    protected Deck player1Deck;
    protected Deck player2Deck;

    ArrayList<Card> player1Hand = new ArrayList<>();
    ArrayList<Card> player2Hand = new ArrayList<>();

    Hero hero1;
    Hero hero2;

    ObjectNode addEnvironmentCardsInHandOutput(ObjectNode node, int player_id){
        ArrayNode outputNode = node.withArray("output");
        ArrayList<Card> hand = player_id == 1 ? getPlayer1Hand() : getPlayer2Hand();
        for(Card card: hand){
            if(!card.isPlaceable()) {
                card.addOutputNode(outputNode);
            }
        }
        return node;
    }

    ObjectNode addCardsInHandOutput(ObjectNode node,  int player_id){
        ArrayNode outputNode = node.withArray("output");
        ArrayList<Card> hand = player_id == 1 ? getPlayer1Hand() : getPlayer2Hand();
        for(Card card: hand){
            card.addOutputNode(outputNode);
        }
        return node;
    }

    ObjectNode addCardsOnTableOutput(ObjectNode node){
        ArrayNode outputNode = node.withArray("output");
        for(ArrayList<Minion> row : tableRows){
            ArrayNode rowNode = outputNode.addArray();
            for(Card card: row){
                card.addOutputNode(rowNode);
            }
        }
        return node;
    }

    ObjectNode addFrozenCardsOnTableOutput(ObjectNode node){
        ArrayNode outputNode = node.withArray("output");
        for(ArrayList<Minion> row : tableRows){
            for(Minion card: row){
                if(card.isFrozen()) {
                    card.addOutputNode(outputNode);
                }
            }
        }
        return node;
    }

    public ObjectNode addCardAtPositionOutput(ObjectNode node, int x, int y){
        if(x < 0 || 3 < x || getTableRows()[x].size()-1 < y || y < 0){
            node.put("output", "No card available at that position.");
        }
        else {
            getTableRows()[x].get(y).addOutputNode(node);
        }
        return node;
    }

    void addCardToHand(int player_no){
        if(player_no == 1 && !player1Deck.isEmpty()){
            player1Hand.add(player1Deck.pullCard());
        }
        else if(player_no == 2 && !player2Deck.isEmpty()){
            player2Hand.add(player2Deck.pullCard());
        }
    }

    public Table(){
        for(int i = 0; i<tableRows.length; i++){
            tableRows[i] = new ArrayList<Minion>();
        }
    }

    public void setHero1(Hero hero1) {
        this.hero1 = hero1;
    }

    public void setHero2(Hero hero2) {
        this.hero2 = hero2;
    }

    public Hero getHero1() {
        return hero1;
    }

    public Hero getHero2() {
        return hero2;
    }

    public ArrayList<Card> getPlayer1Hand() {
        return player1Hand;
    }

    public ArrayList<Card> getPlayer2Hand() {
        return player2Hand;
    }

    public void setPlayer1Hand(ArrayList<Card> player1Hand) {
        this.player1Hand = player1Hand;
    }

    public void setPlayer2Hand(ArrayList<Card> player2Hand) {
        this.player2Hand = player2Hand;
    }

    public void setPlayer1Deck(Deck player1Deck) {
        this.player1Deck = player1Deck;
    }

    public void setPlayer2Deck(Deck player2Deck) {
        this.player2Deck = player2Deck;
    }

    public Deck getPlayer1Deck() {
        return player1Deck;
    }

    public Deck getPlayer2Deck() {
        return player2Deck;
    }

    public ArrayList<Minion>[] getTableRows() {
        return tableRows;
    }

    public void setTableRows(ArrayList<Minion>[] tableRows) {
        this.tableRows = tableRows;
    }
}
