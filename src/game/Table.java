package game;

import java.util.ArrayList;

public class Table {

    protected ArrayList<Card>[] tableRows = new ArrayList[4];

    protected Deck player1Deck;
    protected Deck player2Deck;

    ArrayList<Card> player1Hand = new ArrayList<>();
    ArrayList<Card> player2Hand = new ArrayList<>();

    Hero hero1;
    Hero hero2;

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
            tableRows[i] = new ArrayList<Card>();
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

    public ArrayList<Card>[] getTableRows() {
        return tableRows;
    }

    public void setTableRows(ArrayList<Card>[] tableRows) {
        this.tableRows = tableRows;
    }
}
