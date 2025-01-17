package tomorimod.cards.permanentforms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.cards.notshow.utilcards.WholeLife;
import tomorimod.character.Tomori;
import tomorimod.powers.custompowers.WholeLifePower;
import tomorimod.savedata.customdata.PermanentFormsSaveData;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;
import tomorimod.util.CardStats;

import static tomorimod.util.CustomUtils.idToName;

public class SmallMonment extends BaseCard implements PermanentFrom {


    public static final String ID = makeID(SmallMonment.class.getSimpleName());
    public static final CardStats info = new CardStats(
            Tomori.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public boolean isNameChanged=false;

    public static final int MAGIC = 5;
    public static final int UPG_MAGIC = 0;

    public SmallMonment() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        cardsToPreview=new WholeLife();
        tags.add(CardTags.HEALING);
    }

    @Override
    public void triggerOnGlowCheck() {
        if(isNameChanged){
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }else{
            super.triggerOnGlowCheck();
        }
    }

    public void updateDescriptionWithUPG(){
        if(!isNameChanged){
            rawDescription=cardStrings.DESCRIPTION;
            setCustomVar("MN",SaveMusicDiscoverd.getInstance().musicDiscoveredNum);
            rawDescription+=cardStrings.UPGRADE_DESCRIPTION;
        }else{
            name="一辈子";
            rawDescription= cardStrings.EXTENDED_DESCRIPTION[0];
            cardsToPreview=null;
        }
        initializeDescription();
    }

    @Override
    public void applyPowers(){
        if(SaveMusicDiscoverd.getInstance().musicDiscoveredNum>=magicNumber){
            isNameChanged=true;
        }
        super.applyPowers();
        updateDescriptionWithUPG();
    }

    @Override
    public void onMoveToDiscard() {
        if(!isNameChanged){
            rawDescription=cardStrings.DESCRIPTION;
        }else{
            rawDescription=cardStrings.EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        return super.canUse(p,m)&&isNameChanged;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new WholeLifePower(p),0));
        PermanentFormsSaveData.getInstance().addPermanentForms(idToName(ID));
        BaseMonmentCard.removeFromMasterDeck(this);
    }

    @Override
    public AbstractCard makeCopy(){
        return new SmallMonment();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            cardsToPreview.upgrade();
        }
    }

}
