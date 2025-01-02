package tomorimod.cards.special;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
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
import tomorimod.character.Tomori;
import tomorimod.monsters.sakishadow.SakiRightPower;
import tomorimod.monsters.sakishadow.SakiShadowMonster;
import tomorimod.util.CardStats;

public class FearlessLove extends BaseCard implements SpecialCard, WithoutMaterial {
    public static final String ID = makeID(FearlessLove.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.SELF,
            1
    );

    private final int MAGIC=2;
    private final int UPG_MAGIC=0;

    public FearlessLove() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);

    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
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

    @SpireOverride
    public void renderEnergy(SpriteBatch sb){

    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new FearlessLove();
    }


}
