package game;

public class GameStats {
    private int playerOneWins = 0;
    private int playerTwoWins = 0;
    private int playedGames = 0;

    public void incrementPlayerOneWins(){
        playerOneWins++;
    }

    public void incrementPlayerTwoWins(){
        playerTwoWins++;
    }

    public void incrementPlayedGamesNo(){
        playedGames++;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public void setPlayerOneWins(int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public void setPlayerTwoWins(int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }
}
