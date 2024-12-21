package tomorinmod.cards.permanentforms;

import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.actions.ApplyShineAction;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.custompowers.StarDustPower;
import tomorinmod.savedata.customdata.PermanentFormsSaveData;
import tomorinmod.util.CardStats;

import static tomorinmod.util.CustomUtils.idToName;

public class StarDust extends BaseCard {
    public static final String ID = makeID(StarDust.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );


    private final int MAGIC=1;
    private final int UPG_MAGIC=1;

    public StarDust() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            @Override
            public boolean test(AbstractCard card) {
                return card instanceof StarDust && AbstractDungeon.player.hasPower((makeID("ShinePower")))
                        &&AbstractDungeon.player.getPower(makeID("ShinePower")).amount>=10;
            }

            @Override
            public Color getColor(AbstractCard card) {
                return Color.YELLOW.cpy();
            }

            @Override
            public String glowID() {
                return "tomorinmod:StarDustGlow";
            }
        });
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower((makeID("ShinePower")))&&p.getPower(makeID("ShinePower")).amount>=10){
            addToBot(new ApplyPowerAction(p,p,new StarDustPower(p),0));
            BaseMonmentCard.removeFromMasterDeck(this);
            PermanentFormsSaveData.getInstance().addPermanentForms(idToName(ID));
        }
        addToBot(new ApplyShineAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new StarDust();
    }
}