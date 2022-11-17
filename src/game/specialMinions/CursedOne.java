package game.specialMinions;

import fileio.CardInput;
import game.Minion;

public class CursedOne extends Minion {
    public CursedOne(CardInput cardInfo) {
        super(cardInfo);
        this.row = "Back";
    }
}
