package tomorinmod.cards.music;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import tomorinmod.cards.BaseCard;
import tomorinmod.cards.rare.TomorinApotheosis;
import tomorinmod.savedata.CraftingRecipes;
import tomorinmod.savedata.HistoryCraftRecords;
import tomorinmod.tags.CustomTags;
import tomorinmod.util.CardStats;
import tomorinmod.util.CustomUtils;

import javax.smartcardio.Card;

public abstract class BaseMusic extends BaseCard {
    public BaseMusic(String ID, CardStats info) {
        super(ID, info);
        tags.add(CustomTags.MUSIC);
    }

    protected int musicUpgradeBlock;
    protected int musicUpgradeDamage;
    protected int musicUpgradeMagicNumber=0;


    @Override
    public void upgrade() {
        if(TomorinApotheosis.isTomorinApotheosisUsed){
            this.upgradeDamage(musicUpgradeDamage);
            this.upgradeMagicNumber(musicUpgradeMagicNumber);
            this.upgradeBlock(musicUpgradeBlock);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }else{
            if(!this.upgraded){
                this.upgradeDamage(musicUpgradeDamage);
                this.upgradeMagicNumber(musicUpgradeMagicNumber);
                upgradeName();
            }
        }
    }
    @Override
    public void setMaterialAndLevel() {

    }

}
