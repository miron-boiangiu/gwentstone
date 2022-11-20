package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainGame {
    private Input inputData;
    private ArrayNode output;
    private ObjectMapper objectMapper;
    private static final MainGame instance = new MainGame();
    private int currentTurn;
    private int round_no = 0;
    private Table table;
    private boolean gameIsOver = false;

    private GameStats gameStats = new GameStats();

    public void start_game(Input inputData, ArrayNode output, ObjectMapper objectMapper){
        this.output = output;
        this.inputData = inputData;
        this.objectMapper = objectMapper;
        int no_of_games = inputData.getGames().size();

        for(int i = 0; i<no_of_games; i++){
            // Prepare the game
            setGameIsOver(false);
            round_no = 0;
            GameInput current_game = inputData.getGames().get(i);
            table = new Table();
            int player1DeckNo = current_game.getStartGame().getPlayerOneDeckIdx();
            ArrayList<CardInput> player1InputDeck = inputData.getPlayerOneDecks().getDecks().get(player1DeckNo);
            table.setPlayer1Deck( new Deck(player1InputDeck));

            int player2DeckNo = current_game.getStartGame().getPlayerTwoDeckIdx();
            ArrayList<CardInput> player2InputDeck = inputData.getPlayerTwoDecks().getDecks().get(player2DeckNo);
            table.setPlayer2Deck( new Deck(player2InputDeck));

            int shuffle_seed = current_game.getStartGame().getShuffleSeed();

            Collections.shuffle(table.getPlayer1Deck().getCards(), new Random(shuffle_seed));
            Collections.shuffle(table.getPlayer2Deck().getCards(), new Random(shuffle_seed));

            currentTurn = current_game.getStartGame().getStartingPlayer();

            CardInput hero1Info = current_game.getStartGame().getPlayerOneHero();
            CardInput hero2Info = current_game.getStartGame().getPlayerTwoHero();

            table.setHero1((Hero)CardFactory.create_card(hero1Info));
            table.setHero2((Hero)CardFactory.create_card(hero2Info));

            table.addCardToHand(1);
            table.addCardToHand(2);

            // Start parsing the players' actions after finishing the preparations
            ArrayList<ActionsInput> actions = current_game.getActions();
            for(ActionsInput action: actions){
                if(getGameIsOver() && !ActionConstants.DEBUGGING_COMMANDS.contains(action.getCommand())){
                    continue;
                }
                parseAction(action);
                removeDeadCards();
                if(!getGameIsOver() && isAnyHeroDead()){
                    gameStats.incrementPlayedGamesNo();
                    setGameIsOver(true);
                    String winner_no = table.getHero1().getHealth() <= 0 ? "two" : "one";
                    if(winner_no.equals("one")){
                        gameStats.incrementPlayerOneWins();
                    }
                    else {
                        gameStats.incrementPlayerTwoWins();
                    }
                    ObjectNode winnerNode = output.addObject();
                    String output_string = String.format("Player %s killed the enemy hero.", winner_no);
                    winnerNode.put("gameEnded", output_string);
                }
            }
        }
        gameStats = new GameStats();
    }

    private void parseAction(ActionsInput action){
        String command = action.getCommand();
        if(command.equals("getPlayerDeck")){
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            int requested_player_no = action.getPlayerIdx();
            newNode.put("playerIdx", requested_player_no);
            Deck deck_to_output = requested_player_no == 1 ? table.getPlayer1Deck() : table.getPlayer2Deck();
            deck_to_output.addOutputNode(newNode);
        }
        else if(command.equals("getPlayerHero")){
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            int player_id = action.getPlayerIdx();
            Hero hero = player_id == 1 ? table.getHero1() : table.getHero2();
            newNode.put("playerIdx", player_id);
            hero.addOutputNode(newNode);
        }
        else if(command.equals("getPlayerTurn")){
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("output", getCurrentTurn());
        }
        else if(command.equals("endPlayerTurn")){
            round_no++;
            unfreezeCardsForPlayer(currentTurn);
            currentTurn = 3 - currentTurn;
            Hero hero = currentTurn == 1 ? table.getHero1() : table.getHero2();
            if(round_no % 2 == 0){
                table.getHero1().addMana(1 + round_no/2);
                table.getHero2().addMana(1 + round_no/2);
                table.addCardToHand(1);
                table.addCardToHand(2);
            }
            resetHasAttackedForAllCards();
        }
        else if(command.equals("placeCard")){
            Hero hero = currentTurn == 1 ? table.getHero1() : table.getHero2();
            int posOfCard = action.getHandIdx();
            String return_value = placeCard(currentTurn, posOfCard);

            if(return_value != null){
                ObjectNode newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("handIdx", posOfCard);
                newNode.put("error", return_value);
            }
        }
        else if(command.equals("getCardsInHand")){
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            int player_id = action.getPlayerIdx();
            newNode.put("playerIdx", player_id);
            table.addCardsInHandOutput(newNode, player_id);
        }
        else if(command.equals("getPlayerMana")){
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            int player_id = action.getPlayerIdx();
            newNode.put("playerIdx", player_id);
            Hero hero = player_id == 1 ? table.getHero1() : table.getHero2();
            newNode.put("output", hero.getMana());
        } else if(command.equals("getCardsOnTable")){
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            table.addCardsOnTableOutput(newNode);
        } else if (command.equals("getEnvironmentCardsInHand")) {
            ObjectNode newNode = output.addObject();
            int player_id = action.getPlayerIdx();
            newNode.put("playerIdx", player_id);
            newNode.put("command", action.getCommand());
            table.addEnvironmentCardsInHandOutput(newNode, player_id);
        } else if (command.equals("useEnvironmentCard")) {
            int card_pos = action.getHandIdx();
            int affected_row = action.getAffectedRow();
            String outputString = playEnvironmentCard(currentTurn, card_pos, affected_row);
            if(outputString != null){
                ObjectNode newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("handIdx", card_pos);
                newNode.put("affectedRow", affected_row);
                newNode.put("error", outputString);
            }
        } else if (command.equals("getCardAtPosition")) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            int x = action.getX();
            int y = action.getY();
            newNode.put("x", x);
            newNode.put("y", y);
            table.addCardAtPositionOutput(newNode, x, y);
        } else if (command.equals("getFrozenCardsOnTable")){
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            table.addFrozenCardsOnTableOutput(newNode);
        } else if (command.equals("cardUsesAttack")) {
            int attacker_x = action.getCardAttacker().getX();
            int attacker_y = action.getCardAttacker().getY();
            int attacked_x = action.getCardAttacked().getX();
            int attacked_y = action.getCardAttacked().getY();
            Minion attacker = table.getTableRows()[attacker_x].get(attacker_y);
            String error = attacker.attack(attacked_x, attacked_y);
            if(error != null){
                ObjectNode newNode = output.addObject();
                newNode.put("command", action.getCommand());
                ObjectNode attacker_coords = newNode.putObject("cardAttacker");
                attacker_coords.put("x", attacker_x);
                attacker_coords.put("y", attacker_y);
                ObjectNode attacked_coords = newNode.putObject("cardAttacked");
                attacked_coords.put("x", attacked_x);
                attacked_coords.put("y", attacked_y);
                newNode.put("error", error);
            }
        } else if (command.equals("cardUsesAbility")) {
            int attacker_x = action.getCardAttacker().getX();
            int attacker_y = action.getCardAttacker().getY();
            int attacked_x = action.getCardAttacked().getX();
            int attacked_y = action.getCardAttacked().getY();
            Minion attacker = table.getTableRows()[attacker_x].get(attacker_y);
            String error = attacker.useSpecialAbility(attacked_x, attacked_y);
            if(error != null){
                ObjectNode newNode = output.addObject();
                newNode.put("command", action.getCommand());
                ObjectNode attacker_coords = newNode.putObject("cardAttacker");
                attacker_coords.put("x", attacker_x);
                attacker_coords.put("y", attacker_y);
                ObjectNode attacked_coords = newNode.putObject("cardAttacked");
                attacked_coords.put("x", attacked_x);
                attacked_coords.put("y", attacked_y);
                newNode.put("error", error);
            }
        } else if (command.equals("useAttackHero")){
            int attacker_x = action.getCardAttacker().getX();
            int attacker_y = action.getCardAttacker().getY();
            Minion attacker = table.getTableRows()[attacker_x].get(attacker_y);
            String error = attacker.attackHero();
            if(error != null){
                ObjectNode newNode = output.addObject();
                newNode.put("command", action.getCommand());
                ObjectNode attacker_coords = newNode.putObject("cardAttacker");
                attacker_coords.put("x", attacker_x);
                attacker_coords.put("y", attacker_y);
                newNode.put("error", error);
            }
        } else if (command.equals("useHeroAbility")){
            int row = action.getAffectedRow();
            Hero hero = getCurrentTurn() == 1 ? table.getHero1() : table.getHero2();
            String error = hero.useAbility(row);
            if(error != null){
                ObjectNode newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("affectedRow", row);
                newNode.put("error", error);
            }
        } else if (command.equals("getTotalGamesPlayed")) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("output", getGameStats().getPlayedGames());
        } else if (command.equals("getPlayerOneWins")) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("output", getGameStats().getPlayerOneWins());
        } else if (command.equals("getPlayerTwoWins")) {
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("output", getGameStats().getPlayerTwoWins());
        }
    }

    private boolean isAnyHeroDead(){
        return table.getHero1().getHealth() <= 0 || table.getHero2().getHealth() <= 0;
    }

    private void removeDeadCards(){
        for(int i = 0; i<4; i++){
            ArrayList<Minion> minions_to_remove = new ArrayList<>();
            for(Minion minion: table.getTableRows()[i]){
                if(minion.getHealth() <= 0){
                    minions_to_remove.add(minion);
                }
            }
            table.getTableRows()[i].removeIf(minions_to_remove::contains);
        }
    }

    private void unfreezeCardsForPlayer(int player_no){
        int starting_row = (4 - player_no*2);
        for(int i = starting_row; i<=starting_row+1; i++){
            for(Minion minion: table.getTableRows()[i]){
                minion.setFrozen(0);
            }
        }
    }

    private String playEnvironmentCard(int playerNo, int posOfCard, int affectedRow){
        ArrayList<Card> hand = playerNo == 1 ? table.getPlayer1Hand() : table.getPlayer2Hand();
        Hero hero = playerNo == 1 ? table.getHero1() : table.getHero2();
        if(posOfCard+1 > hand.size()){
            return null;
        }
        Card card_to_play = hand.get(posOfCard);
        if(card_to_play.isPlaceable()){
            return "Chosen card is not of type environment.";
        }
        if(!hero.canAfford(card_to_play.getCardInfo().getMana())){
            return "Not enough mana to use environment card.";
        }
        if(card_to_play instanceof EnvironmentCard){
            String error_string = ((EnvironmentCard) card_to_play).useCardEffect(affectedRow);
            if(error_string == null){
                hand.remove(card_to_play);
                hero.decreaseMana(card_to_play.getCardInfo().getMana());
            }
            return error_string;
        }
        return null;
    }

    private String placeCard(int playerNo, int posOfCard){
        ArrayList<Card> hand = playerNo == 1 ? table.getPlayer1Hand() : table.getPlayer2Hand();
        Hero hero = playerNo == 1 ? table.getHero1() : table.getHero2();

        Card cardToPlay = hand.get(posOfCard);
        String row_placeable_on = "";
        int row_no;
        ArrayList<Minion> row;

        if(!cardToPlay.isPlaceable()){
            return "Cannot place environment card on table.";
        }
        if(!hero.canAfford(cardToPlay.getCardInfo().getMana())){
            return "Not enough mana to place card on table.";
        }
        if(cardToPlay instanceof Minion){
            row_placeable_on = ((Minion) cardToPlay).getRow();
        }
        else{
            return "Cannot place environment card on table.";
        }
        if(row_placeable_on.equals("Front")){
            row_no = playerNo == 1 ? 2 : 1;
        }else{
            row_no = playerNo == 1 ? 3 : 0;
        }
        row = table.getTableRows()[row_no];
        if(row.size() >= 5){
            return "Cannot place card on table since row is full.";
        }
        hero.decreaseMana(cardToPlay.getCardInfo().getMana());
        row.add((Minion)cardToPlay);
        hand.remove(cardToPlay);
        return null;
    }

    private void resetHasAttackedForAllCards(){
        table.getHero2().setHasAttacked(false);
        table.getHero1().setHasAttacked(false);
        for(int i = 0; i<4; i++){
            for(Minion minion: table.getTableRows()[i]){
                minion.setHasAttacked(false);
            }
        }
    }

    public static MainGame getInstance(){
        return instance;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getRound_no() {
        return round_no;
    }

    public void setRound_no(int round_no) {
        this.round_no = round_no;
    }

    public Table getTable() {
        return table;
    }

    public void setGameIsOver(boolean gameIsOver) {
        this.gameIsOver = gameIsOver;
    }

    public boolean getGameIsOver(){
        return this.gameIsOver;
    }

    public GameStats getGameStats() {
        return gameStats;
    }

    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }
}
