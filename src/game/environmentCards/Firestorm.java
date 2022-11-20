package game.environmentCards;

import fileio.CardInput;
import game.*;

import java.util.ArrayList;

public class Firestorm extends EnvironmentCard {

    public Firestorm(CardInput cardInfo) {
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

        ArrayList<Minion> deadMinions = new ArrayList<>();
        for(Minion minion: table.getTableRows()[affectedRow]){
            minion.decreaseHealth(1);
            if(minion.getHealth() <= 0){
                deadMinions.add(minion);
            }
        }
        for(Minion minion: deadMinions){
            table.getTableRows()[affectedRow].remove(minion);
        }

        return null;

    }
}
