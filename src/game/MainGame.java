package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;

public class MainGame {

    private Input inputData;
    private ArrayNode output;
    private static final MainGame instance = new MainGame();
    private int currentTurn;

    public void start_game(Input inputData, ArrayNode output){
        this.output = output;
        this.inputData = inputData;

        
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
}
