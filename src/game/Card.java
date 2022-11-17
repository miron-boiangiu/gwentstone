package game;

import fileio.CardInput;

abstract public class Card {
    protected CardInput cardInfo;

    public Card(CardInput cardInfo){
        this.cardInfo = cardInfo;
    }

    public CardInput getCardInfo() {
        return cardInfo;
    }
}
