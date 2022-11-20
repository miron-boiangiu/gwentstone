package game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public final class ActionConstants {
    public static final ArrayList<String> DEBUGGING_COMMANDS = new ArrayList<>(Arrays.asList("getCardsInHand",
                                                                                             "getPlayerDeck",
                                                                                             "getCardsOnTable",
                                                                                             "getPlayerTurn",
                                                                                             "getPlayerHero",
                                                                                             "getCardAtPosition",
                                                                                             "getPlayerMana",
                                                                                             "getEnvironmentCardsInHand",
                                                                                             "getFrozenCardsOnTable",
                                                                                             "getTotalGamesPlayed",
                                                                                             "getPlayerOneWins",
                                                                                             "getPlayerTwoWins"));
    private ActionConstants(){}
}
