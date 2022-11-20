package game.basicMinions;

import fileio.CardInput;
import game.Minion;

public class Warden extends Minion {

    public Warden(CardInput cardInfo) {
        super(cardInfo);
        isTank = true;
    }
}
