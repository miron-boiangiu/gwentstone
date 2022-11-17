package game;

import fileio.CardInput;

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();

    public Deck(ArrayList<CardInput> inputCards){
        for(CardInput inputCard: inputCards){
            cards.add( CardFactory.create_card(inputCard) );
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
