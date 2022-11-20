package game.heroes;

import fileio.CardInput;
import game.Hero;
import game.MainGame;
import game.Minion;
import game.Table;

public class Mudface extends Hero {

    public Mudface(CardInput cardInfo) {
        super(cardInfo);
    }

    public String useAbility(int row) {
        int turn_of_player_no = MainGame.getInstance().getCurrentTurn();
        int enemy_player_no = 3 - turn_of_player_no;
        Table table = MainGame.getInstance().getTable();
        int first_row_of_player = 4 - 2*turn_of_player_no;
        if(!canAfford(getCardInfo().getMana())){
            return "Not enough mana to use hero's ability.";
        } else if (hasAttacked()) {
            return "Hero has already attacked this turn.";
        } else if (!(row == first_row_of_player || row == first_row_of_player + 1 )) {
            return "Selected row does not belong to the current player.";
        }
        for(Minion minion: table.getTableRows()[row]){
            int minion_health = minion.getHealth();
            minion.setHealth(minion_health + 1);
        }
        decreaseMana(getCardInfo().getMana());
        setHasAttacked(true);
        return null;
    }
}
