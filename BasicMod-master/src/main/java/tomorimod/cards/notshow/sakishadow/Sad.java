package tomorimod.cards.notshow.sakishadow;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.notshow.SpecialCard;
import tomorimod.monsters.sakishadow.SakiShadowRightPower;
import tomorimod.monsters.sakishadow.SakiShadowMonster;
import tomorimod.util.CardStats;

public class Sad extends BaseCard implements SpecialCard, WithoutMaterial, SakiShadow {
    public static final String ID = makeID(Sad.class.getSimpleName());
    public static final CardStats info = new CardStats(
            //Tomori.Meta.CARD_COLOR,
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.NONE,
            -2
    );
    public final int MAGIC=3;
    public final int UPG_MAGIC=0;

    public Sad() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }



//    @Override
//    public boolean canUse(AbstractPlayer p,AbstractMonster m){
//        return false;
//    }


    @Override
    public void triggerOnEndOfPlayerTurn() {
        for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster instanceof SakiShadowMonster){
                addToBot(new ApplyPowerAction(monster,AbstractDungeon.player,
                        new SakiShadowRightPower(monster,magicNumber),magicNumber));
            }
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
    @Override
    public boolean canUpgrade(){
        return false;
    }

    @Override
    public void upgrade(){

    }
    @Override
    public AbstractCard makeCopy() {
        return new Sad();
    }


}
