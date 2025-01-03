package tomorimod.cards.special;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tomorimod.cards.BaseCard;
import tomorimod.cards.WithoutMaterial;
import tomorimod.character.Tomori;
import tomorimod.monsters.mutsumi.MutsumiMonster;
import tomorimod.util.CardStats;

public class FearlessDeath extends BaseCard implements SpecialCard, WithoutMaterial {
    public static final String ID = makeID(FearlessDeath.class.getSimpleName());
    private static final CardStats info = new CardStats(
            //Tomori.Meta.CARD_COLOR,
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.NONE,
            -2
    );

    private final int MAGIC=10;
    private final int UPG_MAGIC=0;


    public FearlessDeath() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);

    }

    @Override
    public boolean canUse(AbstractPlayer p,AbstractMonster m){
        return false;
    }

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
    public AbstractCard makeCopy() {
        return new FearlessDeath();
    }


}
