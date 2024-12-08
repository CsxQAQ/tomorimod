package tomorinmod.util;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import tomorinmod.cards.BaseCard;

import java.util.ArrayList;

public class GetModCardsUtils {

    public static ArrayList<AbstractCard> getAllModCards() {
        ArrayList<AbstractCard> modCards = BaseMod.getCustomCardsToAdd();
        return modCards;
    }

}