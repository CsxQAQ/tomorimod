package tomorinmod.cards.permanentforms;

import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.monment.BaseMonmentCard;
import tomorinmod.cards.special.WholeLife;
import tomorinmod.character.MyCharacter;
import tomorinmod.powers.permanentforms.SmallMonmentPower;
import tomorinmod.savedata.customdata.PermanentFormsSaveData;
import tomorinmod.savedata.customdata.SaveMusicDiscoverd;
import tomorinmod.util.CardStats;

import static tomorinmod.util.CustomUtils.idToName;

public class SmallMonment extends BaseCard implements PermanentFrom {


    public static final String ID = makeID(SmallMonment.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private boolean isNameChanged=false;
    private int curMusicDiscoveredNum=-1;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;

    public SmallMonment() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        cardsToPreview=new WholeLife();


    }

    @Override
    public void triggerOnGlowCheck() {
        if(isNameChanged){
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }else{
            glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void changeToWholeLife(){
        name="一辈子";
        rawDescription= CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        cardsToPreview=null;
        curMusicDiscoveredNum=SaveMusicDiscoverd.getInstance().musicDiscoveredNum;
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        return isNameChanged;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new SmallMonmentPower(p),0));
        PermanentFormsSaveData.getInstance().addPermanentForms(idToName(ID));
        BaseMonmentCard.removeFromMasterDeck(this);
    }

    @Override
    public void update(){
        super.update();
        if(SaveMusicDiscoverd.getInstance().musicDiscoveredNum>=magicNumber){
            if(!isNameChanged){
                changeToWholeLife();
                isNameChanged=true;
            }
        }else{
            if(curMusicDiscoveredNum!=SaveMusicDiscoverd.getInstance().musicDiscoveredNum){
                curMusicDiscoveredNum=SaveMusicDiscoverd.getInstance().musicDiscoveredNum;
                rawDescription=CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION+"（已创作数量："+SaveMusicDiscoverd.getInstance().musicDiscoveredNum+"）";
                initializeDescription();
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
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
