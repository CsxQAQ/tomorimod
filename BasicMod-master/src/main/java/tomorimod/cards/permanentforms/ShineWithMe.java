package tomorimod.cards.permanentforms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorimod.cards.BaseCard;
import tomorimod.cards.monment.BaseMonmentCard;
import tomorimod.character.MyCharacter;
import tomorimod.powers.custompowers.ShineWithMePower;
import tomorimod.savedata.customdata.PermanentFormsSaveData;
import tomorimod.savedata.customdata.SaveMusicDiscoverd;
import tomorimod.util.CardStats;

import static tomorimod.util.CustomUtils.idToName;

public class ShineWithMe extends BaseCard implements PermanentFrom {


    public static final String ID = makeID(ShineWithMe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    public ShineWithMe() {
        super(ID, info);
    }

    private int musicDiscovered=-1;

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p, new ShineWithMePower(p)));
        PermanentFormsSaveData.getInstance().addPermanentForms(idToName(ID));
        BaseMonmentCard.removeFromMasterDeck(this);
    }


    public void updateDescription(){
        rawDescription= CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+"（当前数量："+ SaveMusicDiscoverd.getInstance().musicDiscoveredNum+"）";
        initializeDescription();
    }

    @Override
    public void update(){
        super.update();
        if(musicDiscovered!=SaveMusicDiscoverd.getInstance().musicDiscoveredNum){
            musicDiscovered=SaveMusicDiscoverd.getInstance().musicDiscoveredNum;
            updateDescription();
        }
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
