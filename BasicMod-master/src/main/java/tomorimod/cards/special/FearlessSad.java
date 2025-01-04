package tomorimod.cards.special;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.sakishadow.SakiRightPower;
import tomorimod.monsters.sakishadow.SakiShadowMonster;
import tomorimod.util.CardStats;

public class FearlessSad extends BaseCard implements SpecialCard, WithoutMaterial {
    public static final String ID = makeID(FearlessSad.class.getSimpleName());
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

    public FearlessSad() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }



    @Override
    public boolean canUse(AbstractPlayer p,AbstractMonster m){
        return false;
    }


    @Override
    public void triggerOnEndOfPlayerTurn() {
        for(AbstractMonster monster:AbstractDungeon.getCurrRoom().monsters.monsters){
            if(monster instanceof SakiShadowMonster){
                addToBot(new ApplyPowerAction(monster,AbstractDungeon.player,
                        new SakiRightPower(monster,magicNumber),magicNumber));
            }
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new FearlessSad();
    }


}
