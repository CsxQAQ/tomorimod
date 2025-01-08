package tomorimod.cards.notshow;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.util.CardStats;

public class FearlessLove extends BaseCard implements SpecialCard, WithoutMaterial,SakiShadow {
    public static final String ID = makeID(FearlessLove.class.getSimpleName());
    public static final CardStats info = new CardStats(
            //Tomori.Meta.CARD_COLOR,
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.NONE,
            -2
    );

    public final int MAGIC=2;
    public final int UPG_MAGIC=0;

    public FearlessLove() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);

    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new VulnerablePower(AbstractDungeon.player,magicNumber,false),magicNumber));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new WeakPower(AbstractDungeon.player,magicNumber,false),magicNumber));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new FrailPower(AbstractDungeon.player,magicNumber,false),magicNumber));
    }


    @Override
    public boolean canUse(AbstractPlayer p,AbstractMonster m){
        return false;
    }



    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new FearlessLove();
    }


}
