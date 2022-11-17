package game.environmentCards;

import fileio.CardInput;
import game.EnvironmentCard;
import game.MainGame;
import game.Minion;
import game.Table;

public class Winterfell extends EnvironmentCard {
    public Winterfell(CardInput cardInfo) {
        super(cardInfo);
    }

    @Override
    public void useCardEffect(int affectedRow) {
        Table table = MainGame.getInstance().getTable();

        for(Minion minion: table.getTableRows()[affectedRow]){
            minion.setFrozen(2);
        }
    }
}
