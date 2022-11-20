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
    public String useCardEffect(int affectedRow) {
        Table table = MainGame.getInstance().getTable();
        int playerNo = MainGame.getInstance().getCurrentTurn();

        if((playerNo == 1 && (affectedRow == 2 || affectedRow == 3)) ||
                (playerNo == 2 && (affectedRow == 0 || affectedRow == 1))){
            return "Chosen row does not belong to the enemy.";
        }

        for(Minion minion: table.getTableRows()[affectedRow]){
            minion.setFrozen(1);
        }
        return null;
    }
}
