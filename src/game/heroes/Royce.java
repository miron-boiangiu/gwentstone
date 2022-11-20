package game.heroes;

import fileio.CardInput;
import game.Hero;
import game.MainGame;
import game.Minion;
import game.Table;

public class Royce extends Hero {
    public Royce(CardInput cardInfo) {
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
        } else if (row == first_row_of_player || row == first_row_of_player + 1 ) {
            return "Selected row does not belong to the enemy.";
        }
        Minion minion_to_freeze = null;
        int max_damage = -1;
        for(Minion minion: table.getTableRows()[row]){
            if(minion.getDamage() > max_damage){
                max_damage = minion.getHealth();
                minion_to_freeze = minion;
            }
        }
        if(minion_to_freeze != null) {
            minion_to_freeze.setFrozen(2);
            decreaseMana(getCardInfo().getMana());
            setHasAttacked(true);
        }
        return null;
    }
}
