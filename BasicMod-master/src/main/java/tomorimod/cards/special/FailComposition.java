
package tomorimod.cards.special;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.util.CardStats;

//这个设置为BaseMusicCard可能会出问题
public class FailComposition extends BaseCard implements WithoutMaterial,SpecialCard {
    public static final String ID = makeID(FailComposition.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private final int MAGIC=5;
    private final int UPG_MAGIC=0;


    public FailComposition() {
        super(ID, info);
        purgeOnUse=true;
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public boolean canUpgrade(){
        return false;
    }

    @Override
    public void upgrade(){

    }

//    @Override
//    public void triggerOnEndOfTurnForPlayingCard() {
//        addToBot(new AbstractGameAction() {
//            @Override
//            public void update() {
//                if (AbstractDungeon.player.hand.contains(FailComposition.this)) {
//                    AbstractDungeon.player.hand.removeCard(FailComposition.this);
//                }
//                if (AbstractDungeon.player.limbo.contains(FailComposition.this)) {
//                    AbstractDungeon.player.limbo.removeCard(FailComposition.this);
//                }
//
//                AbstractDungeon.effectList.add(new ExhaustCardEffect(FailComposition.this));
//
//                isDone = true;
//            }
//        });
//    }

//    @Override
//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//        return false;
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p,p,magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FailComposition();
    }

}
