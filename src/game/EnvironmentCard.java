package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public abstract class EnvironmentCard extends Card{
    public EnvironmentCard(CardInput cardInfo) {
        super(cardInfo);
    }


}
