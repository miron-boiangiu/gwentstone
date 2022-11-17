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

    public void start_game(Input inputData, ArrayNode output, ObjectMapper objectMapper){
        System.out.println("--------------------------Start game--------------------------");
        this.output = output;
        this.inputData = inputData;
        this.objectMapper = objectMapper;

        int no_of_games = inputData.getGames().size();

        for(int i = 0; i<no_of_games; i++){
            System.out.println("-- Start match");
            // Prepare the game
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
                parseAction(action);
            }
        }
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
            // Unfreeze frozen cards
            round_no++;
            currentTurn = 3 - currentTurn;
            Hero hero = currentTurn == 1 ? table.getHero1() : table.getHero2();

            System.out.println("- Ending turn, player 1 has mana: " + table.getHero1().getMana() + ", Player 2 has mana: " + table.getHero2().getMana());

            if(round_no % 2 == 0){
                System.out.println("[Debugging info] Should add mana: " + (1 + round_no/2));
                table.getHero1().addMana(1 + round_no/2);
                table.getHero2().addMana(1 + round_no/2);
                table.addCardToHand(1);
                table.addCardToHand(2);
            }
        }
        else if(command.equals("placeCard")){
            Hero hero = currentTurn == 1 ? table.getHero1() : table.getHero2();
            int posOfCard = action.getHandIdx();
            String return_value = table.playCard(currentTurn, posOfCard);

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
        }
        else if(command.equals("getCardsOnTable")){
            ObjectNode newNode = output.addObject();
            newNode.put("command", action.getCommand());
            table.addCardsOnTableOutput(newNode);
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
}
