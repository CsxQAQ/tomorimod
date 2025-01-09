package tomorimod.powers.custompowers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tomorimod.cards.music.BaseMusicCard;
import tomorimod.powers.BasePower;

import static tomorimod.TomoriMod.makeID;

public class TomoriApotheosisPower extends BasePower {
    public static final String POWER_ID = makeID(TomoriApotheosisPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TomoriApotheosisPower(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    //private boolean isAnyCardUpgrade=false;

    @Override
    public void atStartOfTurn() {
        flash();
        //isAnyCardUpgrade=false;
        AbstractPlayer p= AbstractDungeon.player;
        for(int i=0;i<amount;i++){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    upgradeAllCardsInGroup(p.hand);
                    upgradeAllCardsInGroup(p.drawPile);
                    upgradeAllCardsInGroup(p.discardPile);
                    upgradeAllCardsInGroup(p.exhaustPile);
                    isDone=true;
                }
            });

        }
        CardCrawlGame.sound.play("CARD_UPGRADE");
    }

    private void upgradeAllCardsInGroup(CardGroup cardGroup) {
        for (AbstractCard c : cardGroup.group) {
            c.upgrade();
        }
    }
}
