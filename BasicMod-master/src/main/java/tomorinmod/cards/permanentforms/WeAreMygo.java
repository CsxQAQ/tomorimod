package tomorinmod.cards.permanentforms;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.WeAreMygoPower;
import tomorinmod.savedata.customdata.PermanentFormsSaveData;
import tomorinmod.util.CardStats;

import static tomorinmod.util.CustomUtils.idToName;

public class WeAreMygo extends BaseCard implements PermanentFrom{

    public static final String ID = makeID(WeAreMygo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private final int MAGIC=3;
    private final int UPG_MAGIC=0;

    public WeAreMygo() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WeAreMygoPower(p)));
        PermanentFormsSaveData.getInstance().addPermanentForms(idToName(ID));
        BaseMonmentCard.removeFromMasterDeck(this);
        AbstractDungeon.rareCardPool.removeCard(ID);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WeAreMygo();
    }
}
