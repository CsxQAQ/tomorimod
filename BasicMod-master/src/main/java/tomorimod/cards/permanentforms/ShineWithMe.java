package tomorimod.cards.permanentforms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.ShineWithMePower;
import tomorimod.savedata.customdata.PermanentFormsSaveData;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;
import tomorimod.util.CardStats;

import static tomorimod.util.CustomUtils.idToName;

public class ShineWithMe extends BaseCard implements PermanentFrom {


    public static final String ID = makeID(ShineWithMe.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    public ShineWithMe() {
        super(ID, info);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p, new ShineWithMePower(p)));
        PermanentFormsSaveData.getInstance().addPermanentForms(idToName(ID));
        BaseMonmentCard.removeFromMasterDeck(this);
    }


    public void updateDescriptionWithUPG(){
        rawDescription=cardStrings.DESCRIPTION;
        setCustomVar("MN",SaveMusicDiscoverd.getInstance().musicDiscoveredNum);
        rawDescription+=cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        updateDescriptionWithUPG();
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription=cardStrings.DESCRIPTION;
        initializeDescription();
    }


    @Override
    public AbstractCard makeCopy(){
        return new ShineWithMe();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}
