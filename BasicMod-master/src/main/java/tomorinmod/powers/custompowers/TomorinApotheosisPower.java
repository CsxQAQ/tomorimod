package tomorinmod.powers.custompowers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorinmod.cards.music.BaseMusicCard;
import tomorinmod.powers.BasePower;

import static tomorinmod.BasicMod.makeID;

public class TomorinApotheosisPower extends BasePower {
    public static final String POWER_ID = makeID(TomorinApotheosisPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TomorinApotheosisPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    private boolean isAnyCardUpgrade=false;

    @Override
    public void atStartOfTurn() {
        isAnyCardUpgrade=false;
        AbstractPlayer p= AbstractDungeon.player;
        upgradeAllCardsInGroup(p.hand);
        upgradeAllCardsInGroup(p.drawPile);
        upgradeAllCardsInGroup(p.discardPile);
        upgradeAllCardsInGroup(p.exhaustPile);
        if(isAnyCardUpgrade){
            this.flash();
            CardCrawlGame.sound.play("CARD_UPGRADE");
        }
    }

    private void upgradeAllCardsInGroup(CardGroup cardGroup) {
        for (AbstractCard c : cardGroup.group) {
            if(c instanceof BaseMusicCard){
                c.upgrade();
                isAnyCardUpgrade=true;
            }else{
                if (c.canUpgrade()) {
                    if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                        c.superFlash();
                    }
                    c.upgrade();
                    c.applyPowers();
                    isAnyCardUpgrade=true;
                }
            }
        }
    }
}
