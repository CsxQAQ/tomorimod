package tomorimod.cards.notshow.sakishadow;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.cards.notshow.SpecialCard;
import tomorimod.util.CardStats;

public class Death extends BaseCard implements SpecialCard, WithoutMaterial, SakiShadow {
    public static final String ID = makeID(Death.class.getSimpleName());
    public static final CardStats info = new CardStats(
            //Tomori.Meta.CARD_COLOR,
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.NONE,
            -2
    );

    public final int MAGIC=10;
    public final int UPG_MAGIC=0;


    public Death() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);

    }

//    @Override
//    public boolean canUse(AbstractPlayer p,AbstractMonster m){
//        return false;
//    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, magicNumber,
                DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }


//    @SpireOverride
//    public void renderEnergy(SpriteBatch sb){
//
//    }


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
        return new Death();
    }


}
