package tomorinmod.cards.forms;

import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.cards.music.FailComposition;
import tomorinmod.monitors.HandleFormsMonitor;
import tomorinmod.util.CardStats;

public abstract class BaseFormCard extends BaseCard {

    public String formPower;

    public static String curForm="";

    public BaseFormCard(String ID, CardStats info) {
        super(ID, info);
    }

    abstract public void setFormPower();

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(curForm!=null&&!curForm.isEmpty()) {
            addToBot(new RemoveSpecificPowerAction(p, p, makeID(curForm)));
        }
        curForm=formPower;
    }

    public static void clear(){
        curForm="";
    }
}
