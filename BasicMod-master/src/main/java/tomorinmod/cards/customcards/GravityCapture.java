package tomorinmod.cards.customcards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import tomorinmod.cards.BaseCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.util.CardStats;

public class GravityCapture extends BaseCard {
    public static final String ID = makeID(GravityCapture.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private final int MAGIC=2;
    private final int UPG_MAGIC=1;

    public GravityCapture() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(p.hasPower(makeID("GravityPower"))){
                    if(m.currentHealth<p.getPower(makeID("GravityPower")).amount*magicNumber){
                        addToTop(new InstantKillAction(m));
                        addToTop(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY, Color.GOLD.cpy())));
                    }
                    else {
                        addToTop(new TextAboveCreatureAction(m, "你的重力还不够！"));
                    }
                }
                isDone=true;
            }
        });
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new GravityCapture();
    }

}
