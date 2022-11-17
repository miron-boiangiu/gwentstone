package game.environmentCards;

import fileio.CardInput;
import game.EnvironmentCard;
import game.MainGame;
import game.Minion;
import game.Table;

public class HeartHound extends EnvironmentCard {
    public HeartHound(CardInput cardInfo) {
        super(cardInfo);
    }

    @Override
    public void useCardEffect(int affectedRow) {
        Table table = MainGame.getInstance().getTable();

        Minion max_health_minion = null;
        int max_health = 0;
        for(Minion minion: table.getTableRows()[affectedRow]){
            if(minion.getHealth() > max_health){
                max_health = minion.getHealth();
                max_health_minion = minion;
            }
        }
        int row_to = 3 - affectedRow;
        if(max_health_minion != null) {
            table.getTableRows()[affectedRow].remove(max_health_minion);
            table.getTableRows()[row_to].add(max_health_minion);
        }
    }
}
