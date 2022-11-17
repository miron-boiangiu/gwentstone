package game.environmentCards;

import fileio.CardInput;
import game.*;

import java.util.ArrayList;

public class Firestorm extends EnvironmentCard {

    public Firestorm(CardInput cardInfo) {
        super(cardInfo);
    }

    @Override
    public void useCardEffect(int affectedRow) {
        Table table = MainGame.getInstance().getTable();

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

    }
}
