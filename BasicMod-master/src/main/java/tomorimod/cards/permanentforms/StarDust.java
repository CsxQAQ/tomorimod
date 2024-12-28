package tomorimod.cards.permanentforms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.actions.ApplyShineAction;
import tomorimod.cards.BaseCard;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.StarDustPower;
import tomorimod.savedata.customdata.PermanentFormsSaveData;
import tomorimod.util.CardStats;

import static tomorimod.util.CustomUtils.idToName;

public class StarDust extends BaseCard implements PermanentFrom{
    public static final String ID = makeID(StarDust.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
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
    }

    @Override
    public void triggerOnGlowCheck() {
        if(AbstractDungeon.player.hasPower((makeID("ShinePower")))
                        &&AbstractDungeon.player.getPower(makeID("ShinePower")).amount>=10){
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }else{
            glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
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