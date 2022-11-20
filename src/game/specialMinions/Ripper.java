package game.specialMinions;

import fileio.CardInput;
import game.MainGame;
import game.Minion;
import game.Table;

public class Ripper extends Minion {

    public Ripper(CardInput cardInfo) {
        super(cardInfo);
        setSpecialMinion(true);
    }

    @Override
    public String useSpecialAbility(int target_x, int target_y) {
        int turn_of_player_no = MainGame.getInstance().getCurrentTurn();
        int enemy_player_no = 3 - turn_of_player_no;
        Table table = MainGame.getInstance().getTable();
        Minion target = table.getTableRows()[target_x].get(target_y);
        int first_row_of_player = 4 - 2*turn_of_player_no;
        if(isFrozen()){
            return "Attacker card is frozen.";
        } else if (getHasAttacked()){
            return "Attacker card has already attacked this turn.";
        } else if (target_x == first_row_of_player || target_x == first_row_of_player+1) {
            return "Attacked card does not belong to the enemy.";
        } else if (table.playerHasTank(enemy_player_no) && (!(target.isTank()))) {
            return "Attacked card is not of type 'Tank'.";
        }
        int target_damage = target.getDamage() - 2;
        target.setDamage(Math.max(0, target_damage));
        setHasAttacked(true);
        return null;
    }
}
