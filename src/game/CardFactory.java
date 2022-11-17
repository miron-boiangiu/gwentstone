package game;

import fileio.CardInput;
import game.basicMinions.Berserker;
import game.basicMinions.Goliath;
import game.basicMinions.Sentinel;
import game.basicMinions.Warden;
import game.environmentCards.Firestorm;
import game.environmentCards.HeartHound;
import game.environmentCards.Winterfell;
import game.heroes.Kocioraw;
import game.heroes.Mudface;
import game.heroes.Royce;
import game.heroes.Thorina;
import game.specialMinions.CursedOne;
import game.specialMinions.Disciple;
import game.specialMinions.Miraj;
import game.specialMinions.Ripper;

public class CardFactory {
    public static Card create_card(CardInput cardInfo){
        if(cardInfo.getName().equals("Goliath")){
            return new Goliath(cardInfo);
        } else if (cardInfo.getName().equals("Sentinel")) {
            return new Sentinel(cardInfo);
        } else if (cardInfo.getName().equals("Berserker")) {
            return new Berserker(cardInfo);
        } else if (cardInfo.getName().equals("Warden")) {
            return new Warden(cardInfo);
        } else if (cardInfo.getName().equals("Firestorm")) {
            return new Firestorm(cardInfo);
        } else if (cardInfo.getName().equals("Heart Hound")) {
            return new HeartHound(cardInfo);
        } else if ( cardInfo.getName().equals("Winterfell") ) {
            return new Winterfell(cardInfo);
        } else if (cardInfo.getName().equals("The Cursed One")) {
            return new CursedOne(cardInfo);
        } else if (cardInfo.getName().equals("Miraj")) {
            return new Miraj(cardInfo);
        } else if (cardInfo.getName().equals("Disciple")) {
            return new Disciple(cardInfo);
        } else if (cardInfo.getName().equals("Lord Royce")) {
            return new Royce(cardInfo);
        } else if (cardInfo.getName().equals("Empress Thorina")) {
            return new Thorina(cardInfo);
        } else if (cardInfo.getName().equals("King Mudface")) {
            return new Mudface(cardInfo);
        } else if (cardInfo.getName().equals("General Kocioraw")) {
            return new Kocioraw(cardInfo);
        } else {
            return new Ripper(cardInfo);
        }
    }
}
