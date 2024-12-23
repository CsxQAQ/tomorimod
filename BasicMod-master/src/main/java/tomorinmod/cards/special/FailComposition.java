
package tomorinmod.cards.special;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.WithoutMaterial;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

//这个设置为BaseMusicCard可能会出问题
public class FailComposition extends BaseCard implements WithoutMaterial {
    public static final String ID = makeID(FailComposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public FailComposition() {
        super(ID, info);
        //isEthereal = true;
    }

    @Override
    public boolean canUpgrade(){
        return false;
    }

    @Override
    public void upgrade(){

    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player.hand.contains(FailComposition.this)) {
                    AbstractDungeon.player.hand.removeCard(FailComposition.this);
                }
                if (AbstractDungeon.player.limbo.contains(FailComposition.this)) {
                    AbstractDungeon.player.limbo.removeCard(FailComposition.this);
                }

                AbstractDungeon.effectList.add(new ExhaustCardEffect(FailComposition.this));

                isDone = true;
            }
        });
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new FailComposition();
    }

}
