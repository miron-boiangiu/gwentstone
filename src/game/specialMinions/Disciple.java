package game.specialMinions;

import fileio.CardInput;
import game.Minion;

public class Disciple extends Minion {
    public Disciple(CardInput cardInfo) {
        super(cardInfo);
        this.row = "Back";
    }
}
