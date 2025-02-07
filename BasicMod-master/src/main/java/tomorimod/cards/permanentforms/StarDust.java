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
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );


    public static final int MAGIC=1;
    public static final int UPG_MAGIC=1;
    public static final int SHINE_NUM=3;

    public StarDust() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        tags.add(CardTags.HEALING);
        setCustomVar("SN",SHINE_NUM);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new StarDustPower(p)));
        BaseMonmentCard.removeFromMasterDeck(this);
        PermanentFormsSaveData.getInstance().addPermanentForms(idToName(ID));
    }

    @Override
    public AbstractCard makeCopy() {
        return new StarDust();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}